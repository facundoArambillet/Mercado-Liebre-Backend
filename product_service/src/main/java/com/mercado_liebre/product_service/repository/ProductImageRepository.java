package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.productImage.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
}
