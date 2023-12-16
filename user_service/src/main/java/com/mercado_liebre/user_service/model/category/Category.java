package com.mercado_liebre.user_service.model.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercado_liebre.user_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.user_service.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="id_type", nullable = false, foreignKey = @ForeignKey(name = "fk_categories_category_families",
            foreignKeyDefinition = "FOREIGN KEY (id_type) REFERENCES category_families(id_type) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    private CategoryFamily categoryFamily;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}

