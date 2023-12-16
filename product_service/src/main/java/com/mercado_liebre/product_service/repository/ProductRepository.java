package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByName(String name);
    @Query("SELECT p FROM Product p WHERE p.weeklyOffer = true ORDER BY idProduct DESC LIMIT 4")
    List<Product> findProductsByWeeklyOffer();
//    @Query("SELECT p FROM Product p INNER JOIN ProductOffer po WHERE p.idProduct = ProductOffer.product.idProduct")
//    List<Product> findProductsInOffer();
    @Query("SELECT p FROM Product p WHERE p.idProduct IN (SELECT po.product.idProduct FROM ProductOffer po)")
    List<Product> findProductsInOffer();
    @Query("SELECT p FROM Product p INNER JOIN Category c ON p.category.name = c.name "  +
            "WHERE p.category.name = :categoryName ORDER BY p.idProduct DESC LIMIT 4")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);
    @Query("SELECT p FROM Product p INNER JOIN Category c ON p.category.name = c.name " +
            "WHERE p.category.name = :categoryName ORDER BY p.idProduct DESC LIMIT 4")
    List<Product> findByLatestCategoryInUserHistory(@Param("categoryName") String categoryName);
    @Query("SELECT p FROM Product p INNER JOIN Category c ON p.category.idCategory = c.idCategory " +
            "INNER JOIN CategoryFamily cf ON c.categoryFamily.idType = cf.idType WHERE cf.idType = :idCategoryFamily " +
            "ORDER BY idProduct DESC LIMIT 4")
    List<Product> findByIdCategoryFamily(@Param("idCategoryFamily") Long idCategoryFamily);
    @Query("SELECT p FROM Product p INNER JOIN Category c ON p.category.idCategory = c.idCategory " +
            "INNER JOIN CategoryFamily cf ON c.categoryFamily.idType = cf.idType WHERE cf.type = :categoryFamilyType " +
            "ORDER BY idProduct DESC LIMIT 4")
    List<Product> findByTypeCategoryFamily(@Param("categoryFamilyType") String categoryFamilyType);

}
