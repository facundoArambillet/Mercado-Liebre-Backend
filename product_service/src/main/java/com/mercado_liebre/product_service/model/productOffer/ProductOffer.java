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
    @Column(name = "id_offer", nullable = false)
    private Long idProductOffer;
    @Column(name = "discount_percentage", nullable = false)
    private double discountPercentage;
    @Column(nullable = false)
    private double total;

    @OneToOne
    @JoinColumn(name = "id_product" , nullable = false, foreignKey = @ForeignKey(name = "fk_product_offers_products",
            foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES products(id_product) ON DELETE CASCADE ON UPDATE CASCADE" )
    )
    private Product product;
}
