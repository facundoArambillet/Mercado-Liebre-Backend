package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.repository.CategoryFamilyRepository;
import com.mercado_liebre.product_service.repository.CategoryRepository;
import org.apache.http.HttpException;
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
public class CategoryServiceTest {
    @Mock
    private CategoryFamilyRepository categoryFamilyRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService = new CategoryServiceImpl();

    @Mock
    private Category category;
    @Mock
    private CategoryFamily categoryFamily;

    private CategoryDTO categoryDTO;
    private CategoryFamilyDTO categoryFamilyDTO;


    @Test
    public void givenCategories_whenGetAll_thenListShouldNotBeEmpty() {
        Category category_2 = new Category();

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category,category_2));

        assertTrue(categoryService.getAll().size() == 2,"The lists should contain the category and category_2");
        assertFalse(categoryService.getAll().isEmpty(),"The lists should contain two categories");
    }

    @Test
    public void givenIdCategory_whenGetById_thenCategoryNotBeNull() {
        Category category_2 = new Category();
        category_2.setIdCategory(1L);

        when(categoryRepository.findById(category_2.getIdCategory())).thenReturn(Optional.ofNullable(category));

        assertNotNull(categoryService.getById(category_2.getIdCategory()),"The method should return category");
    }

    @Test
    public void givenExistingCategory_whenCreate_thenReturnThrowException() {
        ResponseException responseException = new ResponseException("Category already exist", null, HttpStatus.BAD_REQUEST);

        when(categoryRepository.findByName(category.getName()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class,
                () -> categoryService.createCategory(category), "The method should return ResponseException"
        );

        assertEquals("Category already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidCategoryId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That category does not exist", null, HttpStatus.NOT_FOUND);

        when(categoryRepository.findById(idInvalid))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                categoryService.updateCategory(2L,categoryDTO),"The method should return ResponseException"
        );

        assertEquals("That category does not exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND,exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategory_whenUpdate_thenReturnOk() {
        Category category = new Category();
        category.setIdCategory(1L);
        categoryFamilyDTO = new CategoryFamilyDTO();
        categoryFamilyDTO.setIdType(1L);
        categoryDTO = new CategoryDTO(1L,"Test Name",categoryFamilyDTO);

        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(category));
        when(categoryFamilyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(categoryFamily));

        CategoryDTO categoryDtoUpdated = categoryService.updateCategory(category.getIdCategory(),categoryDTO);

        assertEquals(categoryDtoUpdated.getName(),categoryDTO.getName(), "The name should be 'Test name' in both cases");
    }

    @Test
    public void givenCategoryFamilyWithNotExist_whenCreateCategory_thenReturnThrowException() {
        Category category_2 = new Category(2L, "Test Name", categoryFamily);
        ResponseException responseException = new ResponseException("Category Family does not exist", null, HttpStatus.BAD_REQUEST);

        when(categoryFamilyRepository.findById(categoryFamily.getIdType()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                categoryService.createCategory(category_2), "The method should return ResponseException");

        verify(categoryRepository, never()).save(any());

        assertEquals("Category Family does not exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST,exceptionCaptured.getHttpStatus());
    }
}
