package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String name);

    @Query("SELECT c.id_category, c.name, COUNT(p.id_product) as productCount FROM categories c " +
            "JOIN products p ON c.id_category = p.id_category " +
            "GROUP BY c.id_category, c.name " +
            "ORDER BY productCount DESC " +
            "LIMIT 6")
    List<Product> findTopCategoriesWithMostProducts();
}
