package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.category.CategoryMapper;
import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttribute;
import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttributeDTO;
import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttributeMapper;
import com.mercado_liebre.product_service.repository.CategoryAttributeRepository;
import com.mercado_liebre.product_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryAttributeServiceImpl implements CategoryAttributeService {

    @Autowired
    private CategoryAttributeRepository categoryAttributeRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryAttributeDTO> getAll() {
        try {
            List<CategoryAttribute> categoryAttributes = categoryAttributeRepository.findAll();
            List<CategoryAttributeDTO> categoryAttributeDTOS = categoryAttributes.stream().map(
                    categoryAttribute -> CategoryAttributeMapper.mapper.categoryAttributeToCategoryAttributeDto(categoryAttribute))
                    .collect(Collectors.toList());

            return categoryAttributeDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<CategoryAttributeDTO> getById(Long idCategoryAttribute) {
        try {
            Optional<CategoryAttribute> categoryAttributeFound = categoryAttributeRepository.findById(idCategoryAttribute);
            if (categoryAttributeFound.isPresent()) {
                CategoryAttribute categoryAttribute = categoryAttributeFound.get();
                CategoryAttributeDTO categoryAttributeDTO = CategoryAttributeMapper.mapper.categoryAttributeToCategoryAttributeDto(categoryAttribute);

                return Optional.ofNullable(categoryAttributeDTO);
            } else {
                throw new ResponseException("CategoryAttribute not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching category attribute", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public  Optional<CategoryAttributeDTO> getByName(String name) {
        try {
            Optional<CategoryAttribute> categoryAttributeFound = categoryAttributeRepository.findByName(name);
            if(categoryAttributeFound.isPresent()) {
                CategoryAttribute categoryAttribute = categoryAttributeFound.get();
                CategoryAttributeDTO categoryAttributeDTO = CategoryAttributeMapper.mapper.categoryAttributeToCategoryAttributeDto(categoryAttribute);

                return Optional.ofNullable(categoryAttributeDTO);
            } else {
                throw new ResponseException("CategoryAttribute not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By Name category attribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CategoryAttribute createCategoryAttribute(CategoryAttribute categoryAttribute) {
        try {
            Optional<CategoryAttribute> categoryAttributeFound = categoryAttributeRepository.findByName(categoryAttribute.getName());
            if(categoryAttributeFound.isPresent()) {
                throw new ResponseException("CategoryAttribute already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                Long idCategory = categoryAttribute.getCategory().getIdCategory();
                Optional<Category> categoryFound = categoryRepository.findById(idCategory);
                if(categoryFound.isPresent()) {
                    return categoryAttributeRepository.save(categoryAttribute);
                } else {
                    throw new ResponseException("Category does not exist", null, HttpStatus.BAD_REQUEST);
                }

            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create category attribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public CategoryAttributeDTO updateCategoryAttribute(Long idCategoryAttribute, CategoryAttributeDTO categoryAttributeDTO) {
        try {
            Optional<CategoryAttribute> categoryAttributeFound = categoryAttributeRepository.findById(idCategoryAttribute);
            if(categoryAttributeFound.isPresent()) {
                Long idCategory = categoryAttributeDTO.getCategory().getIdCategory();
                Optional<Category> categoryFound = categoryRepository.findById(idCategory);
                if(categoryFound.isPresent()) {
                    CategoryAttribute categoryAttributeUpdated = categoryAttributeFound.get();
                    Category category = CategoryMapper.mapper.categoryDtoToCategory(categoryAttributeDTO.getCategory());
                    categoryAttributeUpdated.setName(categoryAttributeDTO.getName());
                    categoryAttributeUpdated.setCategory(category);
                    categoryAttributeRepository.save(categoryAttributeUpdated);

                   CategoryAttributeDTO categoryAttributeDtoUpdated = CategoryAttributeMapper.mapper.categoryAttributeToCategoryAttributeDto(categoryAttributeUpdated);

                    return categoryAttributeDtoUpdated;
                } else {
                    throw new ResponseException("Category does not exist", null, HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new ResponseException("That category attribute does not exist ", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update category attribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public CategoryAttributeDTO deleteCategoryAttribute(Long idCategoryAttribute) {
        try {
            Optional<CategoryAttribute> categoryAttributeFound = categoryAttributeRepository.findById(idCategoryAttribute);
            if(categoryAttributeFound.isPresent()) {
                CategoryAttribute categoryAttributeDelete = categoryAttributeFound.get();
                CategoryAttributeDTO categoryAttributeDTO = CategoryAttributeMapper.mapper.categoryAttributeToCategoryAttributeDto(categoryAttributeDelete);
                categoryAttributeRepository.delete(categoryAttributeDelete);

                return categoryAttributeDTO;
            } else {
                throw new ResponseException("That category attribute does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete category attribute", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
