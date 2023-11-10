package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.service.CategoryFamilyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/category-family")
public class CategoryFamilyController {
    @Autowired
    private CategoryFamilyServiceImpl categoryFamilyService;
    @GetMapping
    public List<CategoryFamilyDTO> getAll() {
        return categoryFamilyService.getAll();
    }
    @GetMapping("/{idCategoryFamily}")
    public Optional<CategoryFamilyDTO> getById(@PathVariable("idCategoryFamily") Long idCategoryFamily) {
        return categoryFamilyService.getById(idCategoryFamily);
    }
    @GetMapping("/type/{categoryFamilyType}")
    public  Optional<CategoryFamilyDTO> getByType(@PathVariable("categoryFamilyType") String categoryFamilyType) {
        return categoryFamilyService.getByType(categoryFamilyType);
    }
    @PostMapping
    public CategoryFamily createCategoryFamily(@RequestBody CategoryFamily categoryFamily) {
        return categoryFamilyService.createCategoryFamily(categoryFamily);
    }
    @PutMapping("/{idCategoryFamily}")
    public CategoryFamilyDTO updateCategoryFamily(@PathVariable("idCategoryFamily")Long idCategoryFamily,
                                               @RequestBody CategoryFamilyDTO categoryFamilyDTO) {
        return categoryFamilyService.updateCategoryFamily(idCategoryFamily, categoryFamilyDTO);
    }
    @DeleteMapping("/{idCategoryFamily}")
    public CategoryFamilyDTO deleteCategoryFamily(@PathVariable("idCategoryFamily") Long idCategoryFamily) {
        return categoryFamilyService.deleteCategoryFamily(idCategoryFamily);
    }
}
