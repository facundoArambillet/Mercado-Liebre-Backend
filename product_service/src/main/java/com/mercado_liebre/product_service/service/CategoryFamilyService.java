package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryFamilyService {
    List<CategoryFamilyDTO> getAll();
    Optional<CategoryFamilyDTO> getById(Long id);
    Optional<CategoryFamilyDTO> getByType(String type);
    CategoryFamily createCategoryFamily(CategoryFamily categoryFamily);
    CategoryFamilyDTO updateCategoryFamily(Long id, CategoryFamilyDTO categoryFamilyDTO);
    CategoryFamilyDTO deleteCategoryFamily(Long id);
}
