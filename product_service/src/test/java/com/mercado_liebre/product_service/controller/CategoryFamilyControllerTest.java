package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.service.CategoryFamilyServiceImpl;
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
public class CategoryFamilyControllerTest {

    @Mock
    private CategoryFamilyServiceImpl categoryFamilyService;
    @InjectMocks
    private CategoryFamilyController categoryFamilyController;
    @Mock
    private CategoryFamily categoryFamily;

    private CategoryFamilyDTO categoryFamilyDTO;

    @Test
    public void givenCategoryFamilies_whenGetAll_thenListShouldNotBeEmpty() {
        CategoryFamilyDTO categoryFamily_2 = new CategoryFamilyDTO();

        when(categoryFamilyService.getAll()).thenReturn(Arrays.asList(categoryFamilyDTO,categoryFamily_2));

        assertTrue(categoryFamilyController.getAll().size() == 2,"The lists should contain the role 'Type Test' and 'Type 2 Test");
        assertFalse(categoryFamilyController.getAll().isEmpty(),"The lists should contain two roles");
    }

    @Test
    public void givenIdCategoryFamily_whenGetById_thenCategoryFamilyNotBeNull() {
        when(categoryFamilyService.getById(categoryFamily.getIdType())).thenReturn(Optional.ofNullable(categoryFamilyDTO));

        assertNotNull(categoryFamilyController.getById(categoryFamily.getIdType()),"The method should return category family");
    }

    @Test
    public void givenExistingCategoryFamily_whenCreate_thenReturnThrowException() {
        byte[] image = new byte[10];
        CategoryFamily categoryFamily_2 = new CategoryFamily(1L, "Test 2 Type",image);
        ResponseException responseException = new ResponseException("Category Family already exist", null, HttpStatus.BAD_REQUEST);

        when(categoryFamilyService.createCategoryFamily(categoryFamily_2)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                categoryFamilyController.createCategoryFamily(categoryFamily_2), "The method should return ResponseException");

        assertEquals("Category Family already exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST,exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategoryFamily_whenAddToDatabase_thenItIsPersisted() {
        byte[] image = new byte[10];
        CategoryFamily categoryFamily_2 = new CategoryFamily(2L, "Test Type",image);

        when(categoryFamilyService.createCategoryFamily(categoryFamily_2)).thenReturn(categoryFamily_2);

        CategoryFamily savedCategoryFamily = categoryFamilyController.createCategoryFamily(categoryFamily_2);

        assertNotNull(savedCategoryFamily, "The saved object should not be null");
        assertEquals("Test Type", savedCategoryFamily.getType(), "The object type should 'Type Test'");

        // Verifica que el método save del repositorio se haya llamado exactamente una vez con el objeto rol
        verify(categoryFamilyService, times(1)).createCategoryFamily(categoryFamily_2);
    }

    @Test
    public void givenInvalidCategoryFamilyId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That category family does not exist", null, HttpStatus.NOT_FOUND);

        when(categoryFamilyService.updateCategoryFamily(idInvalid,categoryFamilyDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                categoryFamilyController.updateCategoryFamily(idInvalid,categoryFamilyDTO),"The method should return ResponseException");

        assertEquals("That category family does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategoryFamily_whenUpdate_thenReturnOk() {
        byte[] image = new byte[10];
        categoryFamilyDTO = new CategoryFamilyDTO(1L, "Test 2 Type",image);
        CategoryFamily categoryFamily_2 = new CategoryFamily(2L, "Test Type", image);

        when(categoryFamilyService.updateCategoryFamily(categoryFamily_2.getIdType(),categoryFamilyDTO))
                .thenReturn(new CategoryFamilyDTO(categoryFamily_2.getIdType(),
                        categoryFamilyDTO.getType(),categoryFamilyDTO.getImage()));

        CategoryFamilyDTO categoryFamilyDtoUpdated = categoryFamilyController.updateCategoryFamily(categoryFamily_2.getIdType(),categoryFamilyDTO);

        assertEquals(categoryFamilyDtoUpdated.getType(),categoryFamilyDTO.getType(), "The type should be 'Test 2 Type' in both cases");

    }

}
