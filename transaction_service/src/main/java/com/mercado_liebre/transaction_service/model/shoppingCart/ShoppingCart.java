package com.mercado_liebre.transaction_service.model.shoppingCart;

import com.mercado_liebre.transaction_service.model.product.Product;
import com.mercado_liebre.transaction_service.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "shopping_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private Long idCart;
    private double price;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "shopping_cart_has_products",
            joinColumns = @JoinColumn(name = "id_cart"),
            inverseJoinColumns = @JoinColumn(name = "id_product")
    )
    private List<Product> products;

}
