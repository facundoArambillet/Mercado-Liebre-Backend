package com.mercado_liebre.user_service.model.product;

import com.mercado_liebre.user_service.model.category.Category;
import com.mercado_liebre.user_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.user_service.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private String description;
    @Column(name = "is_weekly_offer", nullable = false)
    private boolean weeklyOffer;

    @ManyToOne
    @JoinColumn(name="id_category", nullable = false, foreignKey = @ForeignKey(name = "fk_products_categories",
            foreignKeyDefinition = "FOREIGN KEY (id_category) REFERENCES categories(id_category) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private Category category;
    @ManyToOne
    @JoinColumn(name="id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_products_users",
            foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private User user;
    @ManyToMany
    @JoinTable(
            name = "user_history",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_user"),
            foreignKey = @ForeignKey(name = "fk_user_history_products", foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES products(id_product) ON DELETE CASCADE ON UPDATE CASCADE"),
            inverseForeignKey  = @ForeignKey(name = "fk_user_history_users", foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private Set<User> users;
    @ManyToMany
    @JoinTable(
            name = "products_has_attributes",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_attribute"),
            foreignKey = @ForeignKey(name = "fk_products_has_attributes_products", foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES products(id_product) ON DELETE CASCADE ON UPDATE CASCADE"),
            inverseForeignKey  = @ForeignKey(name = "fk_products_has_attributes_product_attributes", foreignKeyDefinition = "FOREIGN KEY (id_attribute) REFERENCES product_attributes(id_attribute) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private List<ProductAttribute> productAttributes;

}
