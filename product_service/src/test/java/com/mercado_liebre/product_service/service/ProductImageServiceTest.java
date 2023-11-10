package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.productImage.ProductImage;
import com.mercado_liebre.product_service.model.productImage.ProductImageDTO;
import com.mercado_liebre.product_service.repository.ProductImageRepository;
import com.mercado_liebre.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductImageServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductImageRepository productImageRepository;
    @InjectMocks
    private ProductImageServiceImpl productImageService;

    @Mock
    private ProductImage productImage;
    @Mock
    private Product product;
    private ProductImageDTO productImageDTO;
    private ProductImageDTO productImageDTO_2;

    @Test
    public void givenProductImages_whenGetAll_thenListShouldNotBeEmpty() {
        ProductImage productImage_2 = new ProductImage();

        when(productImageRepository.findAll()).thenReturn(Arrays.asList(productImage,productImage_2));

        assertTrue(productImageService.getAll().size() == 2,"The lists should contain the productImage and productImage_2");
        assertFalse(productImageService.getAll().isEmpty(),"The lists should contain two productImages");
    }

    @Test
    public void givenIdProductImage_whenGetById_thenProductImageNotBeNull() {
        ProductImage productImage_2 = new ProductImage();
        productImage_2.setIdProductImage(2L);

        when(productImageRepository.findById(productImage_2.getIdProductImage())).thenReturn(Optional.ofNullable(productImage_2));

        assertNotNull(productImageService.getById(productImage_2.getIdProductImage()),"The method should return productImage");
    }

    @Test
    public void givenExistingProductImage_whenCreate_thenReturnThrowException() {
        ResponseException responseException = new ResponseException("That product does not exist", null, HttpStatus.BAD_REQUEST);
        Product product_2 = new Product();
        product_2.setIdProduct(2L);
        ProductImage productImage_2 = new ProductImage();
        productImage_2.setIdProductImage(2L);
        productImage_2.setProduct(product_2);

        when(productRepository.findById(productImage_2.getProduct().getIdProduct()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productImageService.createProductImage(productImage_2), "The method should return ResponseException");

        assertEquals("That product does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidProductImageId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("Product does not exist", null, HttpStatus.BAD_REQUEST);

        when(productImageRepository.findById(idInvalid)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productImageService.updateProductImage(2L,productImageDTO),"The method should return ResponseException");

        assertEquals("Product does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProductImage_whenUpdate_thenReturnOk() {
        byte[] image = new byte[10];
        byte[] image_2 = new byte[5];
        ProductImage productImage_2 = new ProductImage(2L, image, product);
        productImageDTO_2 = new ProductImageDTO();
        productImageDTO_2.setImage(image_2);
        productImageDTO_2.setProduct(product);

        when(productImageRepository.save(productImage_2)).thenReturn(productImage_2);
        when(productImageRepository.findById(productImage_2.getIdProductImage())).thenReturn(Optional.ofNullable(productImage_2));
        when(productRepository.findById(product.getIdProduct())).thenReturn(Optional.ofNullable(product));

        ProductImageDTO productImageDtoUpdated = productImageService.updateProductImage(productImage_2.getIdProductImage(),productImageDTO_2);

        assertArrayEquals(productImageDtoUpdated.getImage(),productImage_2.getImage(), "The image should be 'image_2' in both cases");
    }

}
