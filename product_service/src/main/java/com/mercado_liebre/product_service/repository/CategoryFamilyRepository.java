package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryFamilyRepository extends JpaRepository<CategoryFamily,Long> {
    Optional<CategoryFamily> findByType(String type);

//    @Query("SELECT cf FROM CategoryFamily cf INNER JOIN Category c ON cf.idType = c.categoryFamily.idType" +
//            "INNER JOIN ")
//    Optional<CategoryFamily> findLatestCategoryFamilyByIdUser(Long idUser);
    @Query("SELECT cf FROM CategoryFamily cf INNER JOIN Category c ON cf.idType = c.categoryFamily.idType " +
            "GROUP BY cf.type ORDER BY COUNT(c.idCategory) DESC LIMIT 6")
    List<CategoryFamily> findPopularCategoryFamilies();
}
