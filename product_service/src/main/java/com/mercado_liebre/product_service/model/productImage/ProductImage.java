package com.mercado_liebre.product_service.model.productImage;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "id_product", foreignKey = @ForeignKey(name = "fk_product_images_products",
            foreignKeyDefinition = "FOREIGN KEY (id_product) REFERENCES products(id_product) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private Product product;
}
