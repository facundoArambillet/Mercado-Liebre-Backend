package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String name);

    @Query("SELECT c.idCategory, c.name, COUNT(p.idProduct) as productCount FROM Category c " +
            "INNER JOIN Product p ON c.idCategory = p.category.idCategory " +
            "GROUP BY c.idCategory, c.name " +
            "ORDER BY productCount DESC " +
            "LIMIT 6")
    List<Object[]> findTopCategoriesWithMostProducts();

//    @Query("SELECT c FROM Category c WHERE c.Name = :name ORDER BY c.idCategory DESC LIMIT 4")
//    List<Category> findLastFourCategoriesByName(@Param("name") String name);
}
