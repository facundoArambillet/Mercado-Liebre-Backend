package com.mercado_liebre.product_service.model.productImage;

import com.mercado_liebre.product_service.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long idProductImage;
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;
}
