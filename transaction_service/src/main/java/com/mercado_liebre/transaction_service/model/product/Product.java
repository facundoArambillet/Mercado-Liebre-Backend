package com.mercado_liebre.transaction_service.model.product;

import com.mercado_liebre.transaction_service.model.category.Category;
import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlan;
import com.mercado_liebre.transaction_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.transaction_service.model.user.User;
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
    private String name;
    private double price;
    private int stock;
    private String description;
    @Column(name = "is_weekly_offer")
    private boolean isWeeklyOffer;

    @ManyToOne
    @JoinColumn(name="id_category")
    private Category category;
    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;
    @ManyToMany
    @JoinTable(
            name = "user_history",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Set<User> users;
    @ManyToMany
    @JoinTable(
            name = "products_has_attributes",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_attribute")
    )
    private List<ProductAttribute> productAttributes;
    @ManyToMany
    @JoinTable(
            name = "products_has_payment_plans",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_payment")
    )
    private List<PaymentPlan> paymentPlans;

}
