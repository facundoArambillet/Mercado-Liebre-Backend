package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.repository.CategoryFamilyRepository;
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
public class CategoryFamilyServiceTest {
    @Mock
    private CategoryFamilyRepository categoryFamilyRepository;
    @InjectMocks
    private CategoryFamilyServiceImpl categoryFamilyService;
    @Mock
    private CategoryFamily categoryFamily;
    private CategoryFamilyDTO categoryFamilyDTO;

    @Test
    public void givenCategoryFamilies_whenGetAll_thenListShouldNotBeEmpty() {
        CategoryFamily categoryFamily_2 = new CategoryFamily();

        when(categoryFamilyRepository.findAll()).thenReturn(Arrays.asList(categoryFamily,categoryFamily_2));

        assertTrue(categoryFamilyService.getAll().size() == 2,"The lists should contain the categoryFamily 'Type Test' and 'Type 2 Test");
        assertFalse(categoryFamilyService.getAll().isEmpty(),"The lists should contain two roles");
    }

    @Test
    public void givenIdCategoryFamily_whenGetById_thenCategoryFamilyNotBeNull() {
        CategoryFamily categoryFamily_2 = new CategoryFamily();
        categoryFamily_2.setIdType(1L);

        when(categoryFamilyRepository.findById(categoryFamily.getIdType())).thenReturn(Optional.ofNullable(categoryFamily_2));

        assertNotNull(categoryFamilyService.getById(categoryFamily.getIdType()),"The method should return categoryFamily_2");
    }

    @Test
    public void givenExistingCategoryFamily_whenCreate_thenReturnThrowException() {
        ResponseException responseException = new ResponseException("Category Family already exist", null, HttpStatus.BAD_REQUEST);

        when(categoryFamilyRepository.findByType(categoryFamily.getType()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                categoryFamilyService.createCategoryFamily(categoryFamily), "The method should return ResponseException");

        assertEquals("Category Family already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidCategoryFamilyId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That category family does not exist", null, HttpStatus.NOT_FOUND);

        when(categoryFamilyRepository.findById(idInvalid)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                categoryFamilyService.updateCategoryFamily(2L,categoryFamilyDTO),"The method should return ResponseException");

        assertEquals("That category family does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategoryFamily_whenUpdate_thenReturnOk() {
        CategoryFamily categoryFamily_2 = new CategoryFamily();
        categoryFamily_2.setIdType(1L);
        categoryFamily_2.setType("Test Type");
        categoryFamilyDTO = new CategoryFamilyDTO();
        categoryFamilyDTO.setType("Test 2 Type");

        when(categoryFamilyRepository.save(categoryFamily_2)).thenReturn(categoryFamily_2);
        when(categoryFamilyRepository.findById(categoryFamily_2.getIdType())).thenReturn(Optional.ofNullable(categoryFamily_2));

        CategoryFamilyDTO categoryFamilyDtoUpdated = categoryFamilyService.updateCategoryFamily(categoryFamily_2.getIdType(),categoryFamilyDTO);

        assertEquals(categoryFamilyDtoUpdated.getType(),categoryFamilyDTO.getType(), "The rol type should be 'Test 2 Type' in both cases");
    }
}
