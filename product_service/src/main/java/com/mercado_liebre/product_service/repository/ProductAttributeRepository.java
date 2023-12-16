package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute,Long> {

    Optional<ProductAttribute> findByName(String name);
}
