package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAll();
    Optional<CategoryDTO> getById(Long id);
    Optional<CategoryDTO> getByName(String name);
    Category createCategory(Category category);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);
}
