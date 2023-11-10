package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.productImage.ProductImage;
import com.mercado_liebre.product_service.model.productImage.ProductImageDTO;
import com.mercado_liebre.product_service.service.ProductImageServiceImpl;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductImageControllerTest {
    @Mock
    private ProductImageServiceImpl productImageService;
    @InjectMocks
    private ProductImageController productImageController;

    @Mock
    private ProductImage productImage;
    @Mock
    private Product product;

    private ProductImageDTO productImageDTO;

    @Test
    public void givenProductImages_whenGetAll_thenListShouldNotBeEmpty() {
        ProductImageDTO productImageDTO_2 = new ProductImageDTO();

        when(productImageService.getAll()).thenReturn(Arrays.asList(productImageDTO,productImageDTO_2));

        assertTrue(productImageController.getAll().size() == 2,"The lists should contain the productImage and productImage_2");
        assertFalse(productImageController.getAll().isEmpty(),"The lists should contain two productImages");
    }

    @Test
    public void givenIdProductImage_whenGetById_thenUserNotBeNull() {
        when(productImageService.getById(product.getIdProduct())).thenReturn(Optional.ofNullable(productImageDTO));

        assertNotNull(productImageController.getById(product.getIdProduct()),"The method should return productImage");
    }

    @Test
    public void givenExistingProductImage_whenCreate_thenReturnThrowException() {
        ResponseException responseException = new ResponseException("That product does not exist", null, HttpStatus.BAD_REQUEST);

        when(productImageService.createProductImage(productImage)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productImageController.createProductImage(productImage), "The method should return ResponseException");

        assertEquals("That product does not exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST,exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidProductImageId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That product image does not exist", null, HttpStatus.BAD_REQUEST);

        when(productImageService.updateProductImage(idInvalid,productImageDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productImageController.updateProductImage(idInvalid,productImageDTO),"The method should return ResponseException");

        assertEquals("That product image does not exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST,exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProductImage_whenUpdate_thenReturnOk() {
        byte[] image_2 = new byte[5];
        ProductImage productImage_2 = new ProductImage();
        productImageDTO = new ProductImageDTO(1L,image_2,product);
        productImage_2.setIdProductImage(2L);
        productImage.setIdProductImage(1L);

        when(productImageService.updateProductImage(productImage_2.getIdProductImage(),productImageDTO)).thenReturn(
                new ProductImageDTO(productImage_2.getIdProductImage(),productImageDTO.getImage(),productImageDTO.getProduct())
        );

        ProductImageDTO productImageDtoUpdated = productImageController.updateProductImage(productImage_2.getIdProductImage(),productImageDTO);

        assertArrayEquals(productImageDtoUpdated.getImage(),productImageDTO.getImage(), "The image should be 'image_2' in both cases");

    }
}
