package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeDTO;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeService {
    List<ProductAttributeDTO> getAll();
    Optional<ProductAttributeDTO> getById(Long id);
    ProductAttribute createProductAttribute(ProductAttribute productAttribute);
    ProductAttributeDTO updateProductAttribute(Long id, ProductAttributeDTO productAttributeDTO);
    ProductAttributeDTO deleteProductAttribute(Long id);
}
