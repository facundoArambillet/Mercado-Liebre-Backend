package com.mercado_liebre.transaction_service.controller;

import com.mercado_liebre.transaction_service.model.product.Product;
import com.mercado_liebre.transaction_service.model.product.ProductDetailDTO;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCartDTO;
import com.mercado_liebre.transaction_service.service.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;
    @GetMapping
    public List<ShoppingCartDTO> getAll() {
        return shoppingCartService.getAll();
    }
    @GetMapping("/{idShoppingCart}")
    public Optional<ShoppingCartDTO> getById(@PathVariable("idShoppingCart") Long idShoppingCart) {
        return shoppingCartService.getById(idShoppingCart);
    }
    @GetMapping("/by-user/{idUser}")
    public Optional<ShoppingCartDTO> getByIdUser(@PathVariable("idUser") Long idUser) {
        return shoppingCartService.getByIdUser(idUser);
    }
    @PostMapping
    public ShoppingCart createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.createShoppingCart(shoppingCart);
    }
    @PostMapping("/insert/{idUser}")
    public ShoppingCartDTO insertProduct(@PathVariable("idUser") Long idUser, @RequestBody ProductDetailDTO productDetailDTO) {
        return shoppingCartService.insertProduct(idUser, productDetailDTO);
    }
//    @PutMapping("/{idShoppingCart}")
//        public ShoppingCart updateShoppingCart(@PathVariable("idShoppingCart")Long idShoppingCart, @RequestBody ShoppingCart shoppingCart) {
//        return shoppingCartService.u(idUser, user);
//    }
    @DeleteMapping("/remove/{idUser}")
    public ShoppingCartDTO removeProduct(@PathVariable("idUser") Long idUser, @RequestBody ProductDetailDTO productDetailDTO) {
        return shoppingCartService.removeProduct(productDetailDTO, idUser);
    }
    @DeleteMapping("/{idShoppingCart}")
    public ShoppingCartDTO deleteShoppingCart(@PathVariable("idShoppingCart") Long idShoppingCart) {
        return shoppingCartService.deleteShoppingCart(idShoppingCart);
    }
}
