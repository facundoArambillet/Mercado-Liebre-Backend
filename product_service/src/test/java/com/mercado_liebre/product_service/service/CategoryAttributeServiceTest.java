package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.category.CategoryMapper;
import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttribute;
import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttributeDTO;
import com.mercado_liebre.product_service.repository.CategoryAttributeRepository;
import com.mercado_liebre.product_service.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryAttributeServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryAttributeRepository categoryAttributeRepository;
    @InjectMocks
    private CategoryAttributeServiceImpl categoryAttributeService;
    @Mock
    private CategoryAttribute categoryAttribute;
    @Mock
    private Category category;
    private CategoryAttributeDTO categoryAttributeDTO;
    private CategoryDTO categoryDTO;

    @Test
    public void givenCategoryAttributes_whenGetAll_thenListShouldNotBeEmpty() {
        CategoryAttribute categoryAttribute_2 = new CategoryAttribute();

        when(categoryAttributeRepository.findAll()).thenReturn(Arrays.asList(categoryAttribute, categoryAttribute_2));

        assertTrue(categoryAttributeService.getAll().size() == 2, "The lists should contain the categoryAttribute and categoryAttribute_2");
        assertFalse(categoryAttributeService.getAll().isEmpty(), "The lists should contain two category attributes");
    }

    @Test
    public void givenIdCategoryAttribute_whenGetById_thenCategoryAttributeNotBeNull() {
        CategoryAttribute categoryAttribute_2 = new CategoryAttribute();
        categoryAttribute_2.setIdAttribute(1L);

        when(categoryAttributeRepository.findById(categoryAttribute_2.getIdAttribute())).thenReturn(Optional.ofNullable(categoryAttribute_2));

        assertNotNull(categoryAttributeService.getById(categoryAttribute_2.getIdAttribute()), "The method should return categoryAttribute");
    }

    @Test
    public void givenExistingCategoryAttribute_whenCreate_thenReturnThrowException() {
        ResponseException responseException = new ResponseException("CategoryAttribute already exist", null, HttpStatus.BAD_REQUEST);

        when(categoryAttributeRepository.findByName(category.getName()))
                .thenThrow(responseException);

        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () -> {
            categoryAttributeService.createCategoryAttribute(categoryAttribute);
        });

        assertEquals("CategoryAttribute already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidCategoryAttributeId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("Category does not exist", null, HttpStatus.BAD_REQUEST);

        when(categoryAttributeRepository.findById(idInvalid))
                .thenThrow(responseException);

        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () -> {
            categoryAttributeService.updateCategoryAttribute(idInvalid, categoryAttributeDTO);
        });

        assertEquals("Category does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategoryAttribute_whenUpdate_thenReturnOk() {
        CategoryAttribute categoryAttribute_2 = new CategoryAttribute();
        Category category_2 = new Category();
        categoryAttribute_2.setIdAttribute(2L);
        category_2.setIdCategory(2L);
        categoryDTO = CategoryMapper.mapper.categoryToCategoryDto(category_2);
        categoryAttributeDTO = new CategoryAttributeDTO(1L, "Test 2 Name",categoryDTO);

        when(categoryAttributeRepository.save(categoryAttribute_2)).thenReturn(categoryAttribute_2);
        when(categoryAttributeRepository.findById(categoryAttribute_2.getIdAttribute())).thenReturn(Optional.ofNullable(categoryAttribute_2));
        when(categoryRepository.findById(category_2.getIdCategory())).thenReturn(Optional.ofNullable(category_2));

        CategoryAttributeDTO categoryAttributeDtoUpdated = categoryAttributeService.updateCategoryAttribute(categoryAttribute_2.getIdAttribute(),categoryAttributeDTO);

        assertEquals(categoryAttributeDtoUpdated.getName(),categoryAttributeDTO.getName(), "The name should be 'Test 2 name' in both cases");
    }

    @Test
    public void givenCategoryWithNotExist_whenCreateCategoryAttribute_thenReturnThrowException() {
        CategoryAttribute categoryAttribute_2 = new CategoryAttribute(2L,"Test 2 name",category);
        ResponseException responseException = new ResponseException("Category does not exist", null, HttpStatus.BAD_REQUEST);

        when(categoryRepository.findById(category.getIdCategory()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
            categoryAttributeService.createCategoryAttribute(categoryAttribute_2)
        );

        verify(categoryAttributeRepository, never()).save(any());

        assertEquals("Category does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }
}
