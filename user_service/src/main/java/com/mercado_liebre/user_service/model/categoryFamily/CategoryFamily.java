package com.mercado_liebre.user_service.model.categoryFamily;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String type;
    private byte[] image;

}
