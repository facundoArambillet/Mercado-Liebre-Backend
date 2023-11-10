package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }
    @GetMapping("/{idCategory}")
    public Optional<CategoryDTO> getById(@PathVariable("idCategory") Long idCategory) {
        return categoryService.getById(idCategory);
    }
    @GetMapping("/type/{categoryName}")
    public  Optional<CategoryDTO> getByName(@PathVariable("categoryName") String categoryName) {
        return categoryService.getByName(categoryName);
    }
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }
    @PutMapping("/{idCategory}")
    public CategoryDTO updateCategory(@PathVariable("idCategory")Long idCategory, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(idCategory, categoryDTO);
    }
    @DeleteMapping("/{idCategory}")
    public CategoryDTO deleteUser(@PathVariable("idCategory") Long idCategory) {
        return categoryService.deleteCategory(idCategory);
    }
}
