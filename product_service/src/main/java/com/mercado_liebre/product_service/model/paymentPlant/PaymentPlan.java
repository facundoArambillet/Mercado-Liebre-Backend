package com.mercado_liebre.product_service.model.paymentPlant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercado_liebre.product_service.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "payment_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment")
    private Long idPayment;
    @Column(nullable = false)
    private int installments;
    @Column(nullable = false)
    private double interest;

//    @ManyToMany(mappedBy = "paymentPlans")
//    @JsonIgnore
//    private List<Product> products;
}
