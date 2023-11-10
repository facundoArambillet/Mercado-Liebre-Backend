package com.mercado_liebre.product_service.repository;

import com.mercado_liebre.product_service.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByName(String name);

    @Query("SELECT * FROM Product WHERE isWeeklyOffer = 1 ORDER BY idProduct DESC LIMIT 4")
    List<Product> findProductsOffer();
}
