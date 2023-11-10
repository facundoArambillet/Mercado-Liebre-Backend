package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttribute;
import com.mercado_liebre.product_service.model.categoryAttribute.CategoryAttributeDTO;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.service.CategoryAttributeServiceImpl;
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
public class CategoryAttributeControllerTest {

    @Mock
    private CategoryAttributeServiceImpl categoryAttributeService;
    @InjectMocks
    private CategoryAttributeController categoryAttributeController;
    @Mock
    private CategoryAttribute categoryAttribute;
    @Mock
    private Category category;
    @Mock
    private CategoryFamily categoryFamily;
    private CategoryAttributeDTO categoryAttributeDTO;
    private CategoryDTO categoryDTO;
    @Test
    public void givenCategoryAttribute_whenGetAll_thenListShouldNotBeEmpty() {
        CategoryAttributeDTO categoryAttribute_2 = new CategoryAttributeDTO();

        when(categoryAttributeService.getAll()).thenReturn(Arrays.asList(categoryAttributeDTO,categoryAttribute_2));

        assertTrue(categoryAttributeController.getAll().size() == 2,"The lists should contain the categoryAttribute and categoryAttribute_2");
        assertFalse(categoryAttributeController.getAll().isEmpty(),"The lists should contain two category attributes");
    }

    @Test
    public void givenIdCategoryAttribute_whenGetById_thenCategoryAttributeNotBeNull() {
        when(categoryAttributeService.getById(categoryAttribute.getIdAttribute())).thenReturn(Optional.ofNullable(categoryAttributeDTO));

        assertNotNull(categoryAttributeController.getById(categoryAttribute.getIdAttribute()),"The method should return categoryAttribute");
    }

    @Test
    public void givenExistingCategoryAttribute_whenCreate_thenReturnThrowException() {
        Category category_2 = new Category(2L,"Test 2 name",categoryFamily);
        CategoryAttribute categoryAttribute_2 = new CategoryAttribute(2L,"Test name",category_2);
        ResponseException responseException = new ResponseException("Category does not exist", null, HttpStatus.BAD_REQUEST);

        when(categoryAttributeService.createCategoryAttribute(categoryAttribute_2)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () -> categoryAttributeController.createCategoryAttribute(categoryAttribute_2), "The method should return ResponseException");

        assertEquals("Category does not exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST,exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategoryAttribute_whenAddToDatabase_thenItIsPersisted() {
        CategoryAttribute categoryAttribute_2 = new CategoryAttribute(2L,"Test Name", category);

        when(categoryAttributeService.createCategoryAttribute(categoryAttribute_2)).thenReturn(categoryAttribute_2);

        CategoryAttribute savedCategoryAttribute = categoryAttributeController.createCategoryAttribute(categoryAttribute_2);

        assertNotNull(savedCategoryAttribute, "The saved object should not be null");
        assertEquals("Test Name", savedCategoryAttribute.getName(), "The object name should 'Test name'");

        // Verifica que el método save del repositorio se haya llamado exactamente una vez con el objeto
        verify(categoryAttributeService, times(1)).createCategoryAttribute(categoryAttribute_2);
    }

    @Test
    public void givenInvalidCategoryAttributeId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That category attribute does not exist", null, HttpStatus.NOT_FOUND);

        when(categoryAttributeService.updateCategoryAttribute(idInvalid,categoryAttributeDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                categoryAttributeController.updateCategoryAttribute(2L,categoryAttributeDTO),"The method should return ResponseException");

        assertEquals("That category attribute does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategoryAttribute_whenUpdate_thenReturnOk() {
        CategoryAttribute categoryAttribute_2 = new CategoryAttribute(2L,"Test 2 name",category);
        categoryAttributeDTO = new CategoryAttributeDTO(1L,"Test Name", categoryDTO);

        when(categoryAttributeService.updateCategoryAttribute(categoryAttribute_2.getIdAttribute(),categoryAttributeDTO)).thenReturn(
                new CategoryAttributeDTO(categoryAttribute_2.getIdAttribute(),categoryAttributeDTO.getName(),
                        categoryAttributeDTO.getCategory())
        );

        CategoryAttributeDTO categoryAttributeDtoUpdated = categoryAttributeController.updateCategoryAttribute(categoryAttribute_2.getIdAttribute(),
                categoryAttributeDTO);

        assertEquals(categoryAttributeDtoUpdated.getName(),categoryAttributeDTO.getName(), "The name should be 'Test 2 name' in both cases");

    }
}
