package com.mercado_liebre.product_service.model.categoryAttribute;

import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAttributeDTO {

    private Long idAttribute;
    private String name;
    private CategoryDTO category;
}
