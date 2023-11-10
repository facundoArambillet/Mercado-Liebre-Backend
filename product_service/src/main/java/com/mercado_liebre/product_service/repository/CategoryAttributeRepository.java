package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute,Long> {

    Optional<CategoryAttribute> findByName(String name);
}
