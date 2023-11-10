package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.productOffer.ProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductOfferRepository extends JpaRepository<ProductOffer,Long> {
    @Query("SELECT po FROM ProductOffer po WHERE po.product.idProduct = :idProduct")
    Optional<ProductOffer> findByIdProduct(@Param("idProduct") Long idProduct);
}
