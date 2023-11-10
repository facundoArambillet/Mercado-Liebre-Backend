package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.product.ProductCreateDTO;
import com.mercado_liebre.product_service.model.product.ProductDTO;
import com.mercado_liebre.product_service.model.product.ProductDetailDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAll();
    Optional<ProductDetailDTO> getById(Long id);
    Optional<ProductDetailDTO> getByName(String name);
    Optional<ProductDTO> getByNameAndInsertIntoHistory(String name, Long idUser);
    ProductCreateDTO createProduct(ProductCreateDTO productCreateDTO);
    ProductDetailDTO updateProduct(Long id, ProductDetailDTO productDetailDTO);
    ProductDTO deleteProduct(Long id);
}
