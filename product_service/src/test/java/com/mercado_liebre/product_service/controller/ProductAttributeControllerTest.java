package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeDTO;
import com.mercado_liebre.product_service.service.ProductAttributeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductAttributeControllerTest {
    @Mock
    private ProductAttributeServiceImpl productAttributeService;
    @InjectMocks
    private ProductAttributeController productAttributeController;

    @Mock
    private ProductAttribute productAttribute;
    private ProductAttributeDTO productAttributeDTO;

    @Test
    public void givenProductAttribute_whenGetAll_thenListShouldNotBeEmpty() {
        ProductAttributeDTO productAttributeDTO_2 = new ProductAttributeDTO();

        when(productAttributeService.getAll()).thenReturn(Arrays.asList(productAttributeDTO,productAttributeDTO_2));

        assertTrue(productAttributeController.getAll().size() == 2,"The lists should contain the productAttribute and productAttribute_2");
        assertFalse(productAttributeController.getAll().isEmpty(),"The lists should contain two productAttributes");
    }

    @Test
    public void givenIdProduct_whenGetById_thenProductNotBeNull() {
        when(productAttributeService.getById(productAttribute.getIdProduct())).thenReturn(Optional.ofNullable(productAttributeDTO));

        assertNotNull(productAttributeController.getById(productAttribute.getIdProduct()),"The method should return productAttribute");
    }

    @Test
    public void givenExistingProductAttribute_whenCreate_thenReturnThrowException() {
        ResponseException responseException = new ResponseException("Product Attribute already exist", null, HttpStatus.BAD_REQUEST);

        when(productAttributeService.createProductAttribute(productAttribute)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productAttributeController.createProductAttribute(productAttribute), "The method should return ResponseException");

        assertEquals("Product Attribute already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProductAttribute_whenAddToDatabase_thenItIsPersisted() {
        ProductAttribute productAttribute_2 = new ProductAttribute(2L,"Test 2 Name", "Test 2 Value");

        when(productAttributeService.createProductAttribute(productAttribute_2)).thenReturn(productAttribute_2);

        ProductAttribute savedProductAttribute = productAttributeController.createProductAttribute(productAttribute_2);

        assertNotNull(savedProductAttribute, "The saved object should not be null");
        assertEquals("Test 2 Name", savedProductAttribute.getName(), "The object name should 'Test name'");

        verify(productAttributeService, times(1)).createProductAttribute(productAttribute_2);
    }

    @Test
    public void givenInvalidProductAttributeId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        productAttributeDTO = new ProductAttributeDTO(1L,"Test Name", "Test Value");
        ResponseException responseException = new ResponseException("That product attribute does not exist", null, HttpStatus.BAD_REQUEST);

        when(productAttributeService.updateProductAttribute(idInvalid,productAttributeDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productAttributeController.updateProductAttribute(idInvalid,productAttributeDTO),"The method should return ResponseException");

    }

    @Test
    public void givenProductAttribute_whenUpdate_thenReturnOk() {
        ProductAttribute productAttribute_2 = new ProductAttribute(2L,"Test 2 Name", "Test 2 Value");
        productAttributeDTO = new ProductAttributeDTO(1L,"Test Name", "Test Value");

        when(productAttributeService.updateProductAttribute(productAttribute_2.getIdProduct(),productAttributeDTO)).thenReturn(
                new ProductAttributeDTO(productAttribute_2.getIdProduct(),productAttributeDTO.getName(),
                        productAttributeDTO.getValue())
        );

        ProductAttributeDTO productAttributeDtoUpdated = productAttributeController.updateProductAttribute(productAttribute_2.getIdProduct(),productAttributeDTO);

        assertEquals(productAttributeDtoUpdated.getName(),productAttributeDTO.getName(), "The name should be 'Test 2 name' in both cases");
    }
}
