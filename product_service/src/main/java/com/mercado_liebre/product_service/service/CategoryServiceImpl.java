package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.category.CategoryMapper;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyMapper;
import com.mercado_liebre.product_service.repository.CategoryFamilyRepository;
import com.mercado_liebre.product_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryFamilyRepository categoryFamilyRepository;

    public List<CategoryDTO> getAll() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDTO> categoryDTOS = categories.stream().map(
                    category -> CategoryMapper.mapper.categoryToCategoryDto(category)).collect(Collectors.toList());

            return categoryDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<CategoryDTO> getById(Long idCategory) {
        try {
            Optional<Category> categoryFound = categoryRepository.findById(idCategory);
            if (categoryFound.isPresent()) {
                Category category = categoryFound.get();
                CategoryDTO categoryDTO = CategoryMapper.mapper.categoryToCategoryDto(category);

                return Optional.ofNullable(categoryDTO);
            } else {
                throw new ResponseException("Category not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching category", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public  Optional<CategoryDTO> getByName(String name) {
        try {
            Optional<Category> categoryFound = categoryRepository.findByName(name);
            if(categoryFound.isPresent()) {
                Category category = categoryFound.get();
                CategoryDTO categoryDTO = CategoryMapper.mapper.categoryToCategoryDto(category);

                return Optional.ofNullable(categoryDTO);
            } else {
                throw new ResponseException("Category not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By Name category", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Category createCategory(Category category) {
        try {
            Optional<Category> categoryFound = categoryRepository.findByName(category.getName());
            if(categoryFound.isPresent()) {
                throw new ResponseException("Category already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                Long idCategoryFamily = category.getCategoryFamily().getIdType();
                Optional<CategoryFamily> categoryFamilyFound = categoryFamilyRepository.findById(idCategoryFamily);
                if(categoryFamilyFound.isPresent()) {
                    return categoryRepository.save(category);
                } else {
                    throw new ResponseException("Category Family does not exist", null, HttpStatus.BAD_REQUEST);
                }

            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create category", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public CategoryDTO updateCategory(Long idCategory, CategoryDTO categoryDTO) {
        try {
            Optional<Category> categoryFound = categoryRepository.findById(idCategory);
            if(categoryFound.isPresent()) {
                Long idCategoryFamily = categoryDTO.getCategoryFamily().getIdType();
                Optional<CategoryFamily> categoryFamilyFound = categoryFamilyRepository.findById(idCategoryFamily);
                if(categoryFamilyFound.isPresent()) {
                    Category categoryUpdated = categoryFound.get();
                    CategoryFamily categoryFamily = CategoryFamilyMapper.mapper.categoryFamilyDtoToCategoryFamily(categoryDTO.getCategoryFamily());
                    categoryUpdated.setName(categoryDTO.getName());
                    categoryUpdated.setCategoryFamily(categoryFamily);
                    categoryRepository.save(categoryUpdated);

                    CategoryDTO categoryDtoUpdated = CategoryMapper.mapper.categoryToCategoryDto(categoryUpdated);
                    return categoryDtoUpdated;
                } else {
                    throw new ResponseException("Category family does not exist", null, HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new ResponseException("That category does not exist ", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update category", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public CategoryDTO deleteCategory(Long idCategory) {
        try {
            Optional<Category> categoryFound = categoryRepository.findById(idCategory);
            if(categoryFound.isPresent()) {
                Category categoryDelete = categoryFound.get();
                CategoryDTO categoryDTO = CategoryMapper.mapper.categoryToCategoryDto(categoryDelete);
                categoryRepository.delete(categoryDelete);

                return categoryDTO;
            } else {
                throw new ResponseException("That category does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete category", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
