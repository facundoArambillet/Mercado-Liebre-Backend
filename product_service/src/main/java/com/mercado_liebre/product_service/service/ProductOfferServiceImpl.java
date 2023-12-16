package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.productOffer.ProductOffer;
import com.mercado_liebre.product_service.model.productOffer.ProductOfferDTO;
import com.mercado_liebre.product_service.model.productOffer.ProductOfferMapper;
import com.mercado_liebre.product_service.repository.ProductOfferRepository;
import com.mercado_liebre.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductOfferServiceImpl implements ProductOfferService{

    @Autowired
    private ProductOfferRepository productOfferRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<ProductOfferDTO> getAll() {
        try {
            List<ProductOffer> productOffers = productOfferRepository.findAll();
            List<ProductOfferDTO> productOfferDTOS = productOffers.stream().map(
                    productOffer -> ProductOfferMapper.mapper.productOfferToProductOfferDto(productOffer))
                    .collect(Collectors.toList());

            return productOfferDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<ProductOfferDTO> getById(Long idProductOffer) {
        try {
            Optional<ProductOffer> productOfferFound = productOfferRepository.findById(idProductOffer);
            if (productOfferFound.isPresent()) {
                ProductOffer productOffer = productOfferFound.get();
                ProductOfferDTO productOfferDTO = ProductOfferMapper.mapper.productOfferToProductOfferDto(productOffer);

                return Optional.ofNullable(productOfferDTO);
            } else {
                throw new ResponseException("Product Offer not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching product offer", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Optional<ProductOfferDTO> getByIdProduct(Long idProduct) {
        try {
            Optional<Product> productFounded = productRepository.findById(idProduct);
            if(productFounded.isPresent()) {
                Optional<ProductOffer> productOfferFounded = productOfferRepository.findByIdProduct(idProduct);
                if(productOfferFounded.isPresent()) {
                    ProductOffer productOffer = productOfferFounded.get();
                    ProductOfferDTO productOfferDTO = ProductOfferMapper.mapper.productOfferToProductOfferDto(productOffer);

                    return Optional.ofNullable(productOfferDTO);
                } else {
                    return null;
                }
            } else {
                throw new ResponseException("Product not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By id product", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ProductOffer createProductOffer(ProductOffer productOffer) {
        try {
            Long idProduct = productOffer.getProduct().getIdProduct();
            Optional<ProductOffer> productOfferFound = productOfferRepository.findByIdProduct(idProduct);
            if(productOfferFound.isPresent()) {
                throw new ResponseException("Product Offer already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                Optional<Product> productFound = productRepository.findById(idProduct);
                if(productFound.isPresent()) {
                    Product product = productFound.get();
                    productOffer.setTotal(product.getPrice() * (1 - (productOffer.getDiscountPercentage() / 100)));
                    return productOfferRepository.save(productOffer);
                } else {
                    throw new ResponseException("That product does not exist", null, HttpStatus.BAD_REQUEST);
                }
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create product offer", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductOfferDTO updateProductOffer(Long idProductOffer, ProductOfferDTO productOfferDTO) {
        try {
            Optional<ProductOffer> productOfferFound = productOfferRepository.findById(idProductOffer);
            if(productOfferFound.isPresent()) {
                Long idProduct = productOfferDTO.getProduct().getIdProduct();
                Optional<Product> productFound = productRepository.findById(idProduct);
                if(productFound.isPresent()) {
                    ProductOffer productOfferUpdate = productOfferFound.get();
                    productOfferUpdate.setDiscountPercentage(productOfferDTO.getDiscountPercentage());
                    productOfferRepository.save(productOfferUpdate);
                    return productOfferDTO;
                } else {
                    throw new ResponseException("Product does not exist", null, HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new ResponseException("That product offer does not exist", null, HttpStatus.BAD_REQUEST);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update product offer", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductOfferDTO deleteProductOffer(Long idProductOffer) {
        try {
            Optional<ProductOffer> productOfferFound = productOfferRepository.findById(idProductOffer);
            if(productOfferFound.isPresent()) {
                ProductOffer productOfferDelete = productOfferFound.get();
                ProductOfferDTO productOfferDTO = ProductOfferMapper.mapper.productOfferToProductOfferDto(productOfferDelete);
                productOfferRepository.delete(productOfferDelete);
                return productOfferDTO;
            } else {
                throw new ResponseException("That product offer does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete product offer", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
