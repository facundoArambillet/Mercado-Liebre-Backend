package com.mercado_liebre.transaction_service.repository;

import com.mercado_liebre.transaction_service.model.shoppingCartHasProduct.ShoppingCartHasProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartHasProductRepository extends JpaRepository<ShoppingCartHasProduct, Long> {

    @Query("SELECT sp FROM ShoppingCartHasProduct AS sp WHERE sp.product.idProduct = :idProduct")
    List<ShoppingCartHasProduct> findByProduct(@Param("idProduct")Long idProduct);

    @Query("SELECT sp FROM ShoppingCartHasProduct AS sp WHERE sp.shoppingCart.idCart = :idCart ")
    List<ShoppingCartHasProduct> findByCart(@Param("idCart") Long idCart);
    @Query("SELECT sp FROM ShoppingCartHasProduct AS sp WHERE sp.shoppingCart.idCart = :idCart " +
            "AND sp.product.idProduct = :idProduct ")
    Optional<ShoppingCartHasProduct> findByCartAndProduct(@Param("idCart") Long idCart,
                                                          @Param("idProduct") Long idProduct);
}
