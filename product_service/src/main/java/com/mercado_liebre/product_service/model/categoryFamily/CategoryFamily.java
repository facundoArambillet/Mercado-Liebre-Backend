package com.mercado_liebre.product_service.model.categoryFamily;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercado_liebre.product_service.model.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category_families")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type")
    private Long idType;
    @Column(nullable = false)
    private String type;
    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] image;

}
