package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttribute;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeDTO;
import com.mercado_liebre.product_service.repository.ProductAttributeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ProductAttributeServiceTest {

    @Mock
    private ProductAttributeRepository productAttributeRepository;
    @InjectMocks
    private ProductAttributeServiceImpl productAttributeService;

    @Mock
    private ProductAttribute productAttribute;
    private ProductAttributeDTO productAttributeDTO;

    @Test
    public void givenProductAttribute_whenGetAll_thenListShouldNotBeEmpty() {
        ProductAttribute productAttribute_2 = new ProductAttribute();

        when(productAttributeRepository.findAll()).thenReturn(Arrays.asList(productAttribute,productAttribute_2));

        assertTrue(productAttributeService.getAll().size() == 2,"The lists should contain the productAttribute and productAttribute_2");
        assertFalse(productAttributeService.getAll().isEmpty(),"The lists should contain two productAttributes");
    }

    @Test
    public void givenIdProductAttribute_whenGetById_thenProductAttributeNotBeNull() {
        when(productAttributeRepository.findById(productAttribute.getIdProduct())).thenReturn(Optional.ofNullable(productAttribute));

        assertNotNull(productAttributeService.getById(productAttribute.getIdProduct()),"The method should return productAttribute");
    }

    @Test
    public void givenExistingProductAttribute_whenCreate_thenReturnThrowException() {
        ResponseException responseException = new ResponseException("Product Attribute already exist", null, HttpStatus.BAD_REQUEST);

        when(productAttributeRepository.findByName(productAttribute.getName()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productAttributeService.createProductAttribute(productAttribute), "The method should return ResponseException");

        assertEquals("Product Attribute already exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST,exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidProductAttributeId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That product attribute does not exist", null, HttpStatus.BAD_REQUEST);

        when(productAttributeRepository.findById(idInvalid)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productAttributeService.updateProductAttribute(2L,productAttributeDTO),"The method should return ResponseException");

        assertEquals("That product attribute does not exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST,exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProductAttribute_whenUpdate_thenReturnOk() {
        ProductAttribute productAttribute_2 = new ProductAttribute(1L, "Test Name", "Test Value");
        productAttributeDTO = new ProductAttributeDTO();
        productAttributeDTO.setName("Test 2 Name");

        when(productAttributeRepository.save(productAttribute_2)).thenReturn(productAttribute_2);
        when(productAttributeRepository.findById(productAttribute_2.getIdProduct())).thenReturn(Optional.ofNullable(productAttribute_2));

        ProductAttributeDTO productAttributeDtoUpdated = productAttributeService.updateProductAttribute(productAttribute_2.getIdProduct(),productAttributeDTO);

        assertEquals(productAttributeDtoUpdated.getName(),productAttributeDTO.getName(), "The name should be 'Test 2 name' in both cases");
    }

}
