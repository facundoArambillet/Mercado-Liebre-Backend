package com.mercado_liebre.user_service.model.productAttribute;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attribute")
    private Long idProduct;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String value;

}
