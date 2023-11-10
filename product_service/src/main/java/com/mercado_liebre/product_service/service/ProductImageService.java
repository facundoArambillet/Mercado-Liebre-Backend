package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.model.productImage.ProductImage;
import com.mercado_liebre.product_service.model.productImage.ProductImageDTO;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {
    List<ProductImageDTO> getAll();
    Optional<ProductImageDTO> getById(Long id);
    ProductImage createProductImage(ProductImage productImage);
    ProductImageDTO updateProductImage(Long id, ProductImageDTO productImageDTO);
    ProductImageDTO deleteProductImage(Long id);
}
