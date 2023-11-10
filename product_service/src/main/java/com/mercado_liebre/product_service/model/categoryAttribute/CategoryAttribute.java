package com.mercado_liebre.product_service.model.categoryAttribute;

import com.mercado_liebre.product_service.model.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category_attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attribute")
    private Long idAttribute;
    private String name;

    @ManyToOne
    @JoinColumn(name="id_category")
    private Category category;
}
