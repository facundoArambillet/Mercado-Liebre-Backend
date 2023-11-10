package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyMapper;
import com.mercado_liebre.product_service.repository.CategoryFamilyRepository;
import com.mercado_liebre.product_service.utils.ImageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryFamilyServiceImpl implements CategoryFamilyService {

    @Autowired
    private CategoryFamilyRepository categoryFamilyRepository;

    private ImageRender imageRender = new ImageRender();

    public List<CategoryFamilyDTO> getAll() {
        try {
            List<CategoryFamily> categoryFamilies = categoryFamilyRepository.findAll();
            List<CategoryFamilyDTO> categoryFamilyDTOS = categoryFamilies.stream().map(
                    categoryFamily -> CategoryFamilyMapper.mapper.categoryFamilyToCategoryFamilyDto(categoryFamily))
                    .collect(Collectors.toList());
            return categoryFamilyDTOS;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public Optional<CategoryFamilyDTO> getById(Long idCategoryFamily) {
        try {
            Optional<CategoryFamily> categoryFamilyFound = categoryFamilyRepository.findById(idCategoryFamily);
            if (categoryFamilyFound.isPresent()) {
                CategoryFamily categoryFamily = categoryFamilyFound.get();
                CategoryFamilyDTO categoryFamilyDTO = CategoryFamilyMapper.mapper.categoryFamilyToCategoryFamilyDto(categoryFamily);

                return Optional.ofNullable(categoryFamilyDTO);
            } else {
                throw new ResponseException("Category Family not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching categoryFamily", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public  Optional<CategoryFamilyDTO> getByType(String type) {
        try {
            Optional<CategoryFamily> categoryFamilyFound = categoryFamilyRepository.findByType(type);
            if(categoryFamilyFound.isPresent()) {
                CategoryFamily categoryFamily = categoryFamilyFound.get();
                CategoryFamilyDTO categoryFamilyDTO = CategoryFamilyMapper.mapper.categoryFamilyToCategoryFamilyDto(categoryFamily);

                return Optional.ofNullable(categoryFamilyDTO);
            } else {
                throw new ResponseException("Category Family not found", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Get By Type categoryFamily", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CategoryFamily createCategoryFamily(CategoryFamily categoryFamily) {
        try {
            Optional<CategoryFamily> categoryFamilyFound = categoryFamilyRepository.findByType(categoryFamily.getType());
            if(categoryFamilyFound.isPresent()) {
                throw new ResponseException("Category Family already exist", null, HttpStatus.BAD_REQUEST);
            } else {
                byte[] image = categoryFamily.getImage();
                categoryFamily.setImage(imageRender.removeBackground(image));

                return categoryFamilyRepository.save(categoryFamily);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create categoryFamily", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public CategoryFamilyDTO updateCategoryFamily(Long idCategoryFamily, CategoryFamilyDTO categoryFamilyDTO) {
        try {
            Optional<CategoryFamily> categoryFamilyFound = categoryFamilyRepository.findById(idCategoryFamily);
            if(categoryFamilyFound.isPresent()) {
                CategoryFamily categoryFamilyUpdated = categoryFamilyFound.get();
                categoryFamilyUpdated.setType(categoryFamilyDTO.getType());
                CategoryFamilyDTO categoryFamilyDtoUpdate= CategoryFamilyMapper.mapper.categoryFamilyToCategoryFamilyDto(categoryFamilyUpdated);
                categoryFamilyRepository.save(categoryFamilyUpdated);

                return categoryFamilyDtoUpdate;
            } else {
                throw new ResponseException("That category family does not exist ", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update categoryFamily", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public CategoryFamilyDTO deleteCategoryFamily(Long idCategoryFamily) {
        try {
            Optional<CategoryFamily> categoryFamilyFound = categoryFamilyRepository.findById(idCategoryFamily);
            if(categoryFamilyFound.isPresent()) {
                CategoryFamily categoryFamilyDelete = categoryFamilyFound.get();
                CategoryFamilyDTO categoryFamilyDTO = CategoryFamilyMapper.mapper.categoryFamilyToCategoryFamilyDto(categoryFamilyDelete);
                categoryFamilyRepository.delete(categoryFamilyDelete);

                return categoryFamilyDTO;
            } else {
                throw new ResponseException("That category family does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete categoryFamily", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
