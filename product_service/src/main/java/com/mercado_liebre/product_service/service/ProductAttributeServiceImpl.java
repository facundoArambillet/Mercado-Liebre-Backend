package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeDTO;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeMapper;
import com.mercado_liebre.product_service.repository.ProductAttributeRepository;
import com.mercado_liebre.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService{

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<ProductAttributeDTO> getAll() {
        try {
            List<ProductAttribute> productAttributes = productAttributeRepository.findAll();
            List<ProductAttributeDTO> productAttributeDTOS = productAttributes.stream().map(
                    productAttribute -> ProductAttributeMapper.mapper.productAttributeToProductAttributeDto(productAttribute))
                    .collect(Collectors.toList());

            return productAttributeDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<ProductAttributeDTO> getById(Long idProductAttribute) {
        try {
            Optional<ProductAttribute> productAttributeFound = productAttributeRepository.findById(idProductAttribute);
            if (productAttributeFound.isPresent()) {
                ProductAttribute productAttribute = productAttributeFound.get();
                ProductAttributeDTO productAttributeDTO = ProductAttributeMapper.mapper.productAttributeToProductAttributeDto(productAttribute);

                return Optional.ofNullable(productAttributeDTO);
            } else {
                throw new ResponseException("Product Attribute not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching product attribute", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductAttribute createProductAttribute(ProductAttribute productAttribute) {
        try {
            Optional<ProductAttribute> productAttributeFound = productAttributeRepository.findByName(productAttribute.getName());
            if(productAttributeFound.isPresent()) {
                throw new ResponseException("Product Attribute already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                return productAttributeRepository.save(productAttribute);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create product attribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductAttributeDTO updateProductAttribute(Long idProductAttribute, ProductAttributeDTO productAttributeDTO) {
        try {
            Optional<ProductAttribute> productAttributeFound = productAttributeRepository.findById(idProductAttribute);
            if(productAttributeFound.isPresent()) {
                ProductAttribute productAttributeUpdate = productAttributeFound.get();
                productAttributeUpdate.setName(productAttributeDTO.getName());
                productAttributeUpdate.setValue(productAttributeDTO.getValue());
                productAttributeRepository.save(productAttributeUpdate);

                return productAttributeDTO;
            } else {
                throw new ResponseException("That product attribute does not exist", null, HttpStatus.BAD_REQUEST);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update product attribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ProductAttributeDTO deleteProductAttribute(Long idProductAttribute) {
        try {
            Optional<ProductAttribute> productAttributeFound = productAttributeRepository.findById(idProductAttribute);
            if(productAttributeFound.isPresent()) {
                ProductAttribute productAttributeDelete = productAttributeFound.get();
                ProductAttributeDTO productAttributeDTO = ProductAttributeMapper.mapper.productAttributeToProductAttributeDto(productAttributeDelete);
                productAttributeRepository.delete(productAttributeDelete);

                return productAttributeDTO;
            } else {
                throw new ResponseException("That product attribute does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete product attribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
