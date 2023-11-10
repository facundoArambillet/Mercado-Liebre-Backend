package com.mercado_liebre.user_service.model.category;

import com.mercado_liebre.user_service.model.categoryFamily.CategoryFamily;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long idCategory;
    private String name;

    @ManyToOne
    @JoinColumn(name="id_type")
    private CategoryFamily categoryFamily;
}

