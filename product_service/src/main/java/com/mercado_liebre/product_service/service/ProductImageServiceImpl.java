package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.product.ProductMapper;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeDTO;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeMapper;
import com.mercado_liebre.product_service.model.productImage.ProductImage;
import com.mercado_liebre.product_service.model.productImage.ProductImageDTO;
import com.mercado_liebre.product_service.model.productImage.ProductImageMapper;
import com.mercado_liebre.product_service.repository.ProductImageRepository;
import com.mercado_liebre.product_service.repository.ProductRepository;
import com.mercado_liebre.product_service.utils.ImageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductRepository productRepository;

    private ImageRender imageRender = new ImageRender();

    public List<ProductImageDTO> getAll() {
        try {
            List<ProductImage> productImages = productImageRepository.findAll();
            List<ProductImageDTO> productImageDTOS = productImages.stream().map(
                            productImage -> ProductImageMapper.mapper.productImageToProductImageDto(productImage))
                    .collect(Collectors.toList());

            return productImageDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ProductImageDTO> getByIdProduct(Long idProduct) {
        try {
            List<ProductImage> productImages = productImageRepository.findImagesByIdProduct(idProduct);
            List<ProductImageDTO> productImageDTOS = productImages.stream().map(
                            productImage -> ProductImageMapper.mapper.productImageToProductImageDto(productImage))
                    .collect(Collectors.toList());

            return productImageDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<ProductImageDTO> getById(Long idProductImage) {
        try {
            Optional<ProductImage> productImageFound = productImageRepository.findById(idProductImage);
            if (productImageFound.isPresent()) {
                ProductImage productImage = productImageFound.get();
                ProductImageDTO productImageDTO = ProductImageMapper.mapper.productImageToProductImageDto(productImage);

                return Optional.ofNullable(productImageDTO);
            } else {
                throw new ResponseException("Product Image not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching product image", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductImage createProductImage(ProductImage productImage) {
        try {
            Long idProduct = productImage.getProduct().getIdProduct();
            Optional<Product> productFound = productRepository.findById(idProduct);
            if(productFound.isPresent()) {
//                byte[] image = productImage.getImage();
//                productImage.setImage(imageRender.removeBackground(image));

                return productImageRepository.save(productImage);
            } else {
                throw new ResponseException("That product does not exist", null, HttpStatus.BAD_REQUEST);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create product image", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductImageDTO updateProductImage(Long idProductImage, ProductImageDTO productImageDTO) {
        try {
            Optional<ProductImage> productImageFound = productImageRepository.findById(idProductImage);
            if(productImageFound.isPresent()) {
                Long idProduct = productImageDTO.getProduct().getIdProduct();
                Optional<Product> productFound = productRepository.findById(idProduct);
                if(productFound.isPresent()) {
                    ProductImage productImageUpdate = productImageFound.get();
                    productImageUpdate.setImage(productImageDTO.getImage());
                    ProductImageDTO productImageDTOUpdate = ProductImageMapper.mapper.productImageToProductImageDto(productImageUpdate);
                    productImageRepository.save(productImageUpdate);

                    return productImageDTOUpdate;
                } else {
                    throw new ResponseException("Product does not exist", null, HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new ResponseException("That product image does not exist", null, HttpStatus.BAD_REQUEST);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update product image", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductImageDTO deleteProductImage(Long idProductImage) {
        try {
            Optional<ProductImage> productImageFound = productImageRepository.findById(idProductImage);
            if(productImageFound.isPresent()) {
                ProductImage productImageDelete = productImageFound.get();
                ProductImageDTO productImageDTO = ProductImageMapper.mapper.productImageToProductImageDto(productImageDelete);
                productImageRepository.delete(productImageDelete);

                return productImageDTO;
            } else {
                throw new ResponseException("That product image does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete product image", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
