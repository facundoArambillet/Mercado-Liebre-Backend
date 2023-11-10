package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttribute;
import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttributeDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryAttributeService {
    List<CategoryAttributeDTO> getAll();
    Optional<CategoryAttributeDTO> getById(Long id);
    Optional<CategoryAttributeDTO> getByName(String name);
    CategoryAttribute createCategoryAttribute(CategoryAttribute categoryAttribute);
    CategoryAttributeDTO updateCategoryAttribute(Long id, CategoryAttributeDTO categoryAttributeDTO);
    CategoryAttributeDTO deleteCategoryAttribute(Long id);
}
