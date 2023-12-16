package com.mercado_liebre.user_service.model.shoppingCart;

import com.mercado_liebre.user_service.model.product.Product;
import com.mercado_liebre.user_service.model.user.User;
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
    @Column(nullable = false)
    private double price;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_shopping_cart_users",
            foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private User user;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "shopping_cart_has_products",
//            joinColumns = @JoinColumn(name = "id_cart"),
//            inverseJoinColumns = @JoinColumn(name = "id_product"),
//            foreignKey = @ForeignKey(name = "fk_shopping_cart_has_products_shopping_cart", foreignKeyDefinition = "FOREIGN KEY (id_cart) REFERENCES shopping_cart(id_cart) ON DELETE CASCADE ON UPDATE CASCADE"),
//            inverseForeignKey  = @ForeignKey(name = "fk_shopping_cart_has_products_products", foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES products(id_product) ON DELETE CASCADE ON UPDATE CASCADE")
//
//    )
//    private List<Product> products;

}
