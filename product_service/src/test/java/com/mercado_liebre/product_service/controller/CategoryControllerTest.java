package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.service.CategoryServiceImpl;
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
public class CategoryControllerTest {
    @Mock
    private CategoryServiceImpl categoryService;
    @InjectMocks
    private CategoryController categoryController = new CategoryController();
    @Mock
    private Category category;
    @Mock
    private CategoryFamily categoryFamily;
    private CategoryDTO categoryDTO;
    private CategoryFamilyDTO categoryFamilyDTO;

    @Test
    public void givenCategories_whenGetAll_thenListShouldNotBeEmpty() {
        categoryDTO = new CategoryDTO();
        CategoryDTO categoryDTO_2 = new CategoryDTO();

        when(categoryService.getAll()).thenReturn(Arrays.asList(categoryDTO,categoryDTO_2));

        assertTrue(categoryController.getAll().size() == 2,"The lists should contain the categoryDTO and categoryDTO_2");
        assertFalse(categoryController.getAll().isEmpty(),"The lists should contain two categoryDTOs");
    }

    @Test
    public void givenIdCategory_whenGetById_thenCategoryNotBeNull() {
        when(categoryService.getById(category.getIdCategory())).thenReturn(Optional.ofNullable(categoryDTO));

        assertNotNull(categoryController.getById(category.getIdCategory()),"The method should return categoryDTO");
    }

    @Test
    public void givenExistingCategory_whenCreate_thenReturnThrowException() {
        ResponseException responseException = new ResponseException("Category already exist", null, HttpStatus.BAD_REQUEST);

        when(categoryService.createCategory(category)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                categoryController.createCategory(category), "The method should return ResponseException");

        assertEquals("Category already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategory_whenAddToDatabase_thenItIsPersisted() {
        Category category_2 = new Category(2L,"Test Name",categoryFamily);

        when(categoryService.createCategory(category_2)).thenReturn(category_2);

        Category savedCategory = categoryController.createCategory(category_2);

        // Verifica que el método save del repositorio se haya llamado exactamente una vez con el objeto
        verify(categoryService, times(1)).createCategory(category_2);

        assertNotNull(savedCategory, "The saved object should not be null");
        assertEquals("Test Name", savedCategory.getName(), "The object name should 'Test Name'");
    }

    @Test
    public void givenInvalidCategoryId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        Category category = new Category();
        categoryDTO = new CategoryDTO();
        CategoryDTO categoryDTO_2 = new CategoryDTO();
        category.setIdCategory(idInvalid);
        categoryDTO.setIdCategory(1L);

        ResponseException responseException = new ResponseException("That category does not exist", null, HttpStatus.NOT_FOUND);

        when(categoryService.updateCategory(category.getIdCategory(),categoryDTO_2)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                categoryController.updateCategory(category.getIdCategory(),categoryDTO_2),"The method should return ResponseException");

        assertEquals("That category does not exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND,exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategory_whenUpdate_thenReturnOk() {
        Category category_2 = new Category();
        category_2.setIdCategory(2L);
        categoryDTO = new CategoryDTO(1L,"Test Name",categoryFamilyDTO);

        when(categoryService.updateCategory(category_2.getIdCategory(),categoryDTO)).thenReturn(
                new CategoryDTO(category_2.getIdCategory(), categoryDTO.getName(), categoryDTO.getCategoryFamily())
        );

        CategoryDTO categoryDtoUpdated = categoryController.updateCategory(category_2.getIdCategory(),categoryDTO);
        assertEquals(categoryDtoUpdated.getName(),categoryDTO.getName(), "The name should be 'Test 2 name' in both cases");

    }
}
