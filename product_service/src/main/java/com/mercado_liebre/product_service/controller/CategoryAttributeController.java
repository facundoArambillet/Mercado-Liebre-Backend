package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttribute;
import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttributeDTO;
import com.mercado_liebre.product_service.service.CategoryAttributeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category-attribute")
public class CategoryAttributeController {
    @Autowired
    private CategoryAttributeServiceImpl categoryAttributeService;
    @GetMapping
    public List<CategoryAttributeDTO> getAll() {
        return categoryAttributeService.getAll();
    }
    @GetMapping("/{idCategoryAttribute}")
    public Optional<CategoryAttributeDTO> getById(@PathVariable("idCategoryAttribute") Long idCategoryAttribute) {
        return categoryAttributeService.getById(idCategoryAttribute);
    }
    @GetMapping("/name/{categoryAttributeName}")
    public  Optional<CategoryAttributeDTO> getByName(@PathVariable("categoryAttributeName") String categoryAttributeName) {
        return categoryAttributeService.getByName(categoryAttributeName);
    }
    @PostMapping
    public CategoryAttribute createCategoryAttribute(@RequestBody CategoryAttribute categoryAttribute) {
        return categoryAttributeService.createCategoryAttribute(categoryAttribute);
    }
    @PutMapping("/{idCategoryAttribute}")
    public CategoryAttributeDTO updateCategoryAttribute(@PathVariable("idCategoryAttribute")Long idCategoryAttribute,
                                                     @RequestBody CategoryAttributeDTO categoryAttributeDTO) {
        return categoryAttributeService.updateCategoryAttribute(idCategoryAttribute, categoryAttributeDTO);
    }
    @DeleteMapping("/{idCategoryAttribute}")
    public CategoryAttributeDTO deleteCategoryAttribute(@PathVariable("idCategoryAttribute") Long idCategoryAttribute) {
        return categoryAttributeService.deleteCategoryAttribute(idCategoryAttribute);
    }
}
