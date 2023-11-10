package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute,Long> {

    Optional<ProductAttribute> findByName(String name);
}
