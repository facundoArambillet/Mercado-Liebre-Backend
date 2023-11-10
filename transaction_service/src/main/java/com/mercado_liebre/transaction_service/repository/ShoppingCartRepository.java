package com.mercado_liebre.transaction_service.repository;

import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

//    Optional<List<Product>> findProductsByIdCart(Long idCart);
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.user.idUser = :idUser")
    Optional<ShoppingCart> findByIdUser(@Param("idUser") Long idUser);
}
