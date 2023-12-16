package com.mercado_liebre.transaction_service.model.product;

import com.mercado_liebre.transaction_service.model.category.Category;
import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlan;
import com.mercado_liebre.transaction_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.transaction_service.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private boolean isWeeklyOffer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_category")
    private Category category;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_user")
    @ToString.Exclude
    private User user;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_history",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_user"),
            foreignKey = @ForeignKey(name = "fk_user_history_products", foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES product(id_product) ON DELETE CASCADE ON UPDATE CASCADE"),
            inverseForeignKey  = @ForeignKey(name = "fk_user_history_users", foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private Set<User> users;
    @ManyToMany
    @JoinTable(
            name = "products_has_attributes",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_attribute"),
            foreignKey = @ForeignKey(name = "fk_products_has_attributes_products", foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES product(id_product) ON DELETE CASCADE ON UPDATE CASCADE"),
            inverseForeignKey  = @ForeignKey(name = "fk_products_has_attributes_product_attributes", foreignKeyDefinition = "FOREIGN KEY (id_attribute) REFERENCES product_attributes(id_attribute) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private List<ProductAttribute> productAttributes;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "products_has_payment_plans",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_payment")
    )
    private List<PaymentPlan> paymentPlans;

}
