package com.mercado_liebre.transaction_service.model.shoppingCartHasProduct;

import com.mercado_liebre.transaction_service.model.product.Product;
import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shopping_cart_has_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartHasProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shopping_cart")
    private Long idShoppingCart;

    @ManyToOne
    @JoinColumn(name = "id_cart", nullable = false)
    private ShoppingCart shoppingCart;
    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;
}
