package com.mercado_liebre.product_service.model.category;

import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long idCategory;
    private String name;
    private CategoryFamilyDTO categoryFamily;
}
