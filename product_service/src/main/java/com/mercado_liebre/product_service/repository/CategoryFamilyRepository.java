package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryFamilyRepository extends JpaRepository<CategoryFamily,Long> {
    Optional<CategoryFamily> findByType(String type);

}
