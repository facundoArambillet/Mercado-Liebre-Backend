package com.mercado_liebre.product_service.model.productOffer;

import com.mercado_liebre.product_service.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_offer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_offer")
    private Long idProductOffer;
    @Column(name = "discount_percentage")
    private double discountPercentage;
    private double total;
    @OneToOne
    @JoinColumn(name = "id_product")
    private Product product;
}
