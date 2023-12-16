package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.productImage.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    @Query("SELECT pi FROM ProductImage pi WHERE pi.product.idProduct = :idProduct")
    List<ProductImage> findImagesByIdProduct(@Param("idProduct") Long idProduct);
}
