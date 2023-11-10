package com.mercado_liebre.transaction_service.service;

import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCartDTO;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {
    List<ShoppingCartDTO> getAll();
    Optional<ShoppingCartDTO> getById(Long id);
    ShoppingCart createShoppingCart(ShoppingCart shoppingCart);
//    ShoppingCart updateShoppingCart(Long id, ShoppingCart shoppingCart);
    ShoppingCartDTO deleteShoppingCart(Long id);
}
