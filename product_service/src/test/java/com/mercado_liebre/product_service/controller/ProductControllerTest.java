package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.product.*;
import com.mercado_liebre.product_service.model.productAttribute.ProductAttributeDTO;
import com.mercado_liebre.product_service.model.user.UserDTO;
import com.mercado_liebre.product_service.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductServiceImpl productService;
    @InjectMocks
    private ProductController productController;

    @Mock
    private Product product;
    private UserDTO userDTO;
    private CategoryDTO categoryDTO;
    private ProductDTO productDTO;
    private ProductDetailDTO productDetailDTO;
    private ProductAttributeDTO productAttributeDTO;

    @Test
    public void givenProducts_whenGetAll_thenListShouldNotBeEmpty() {
        ProductDTO productDTO_2  = new ProductDTO();

        when(productService.getAll()).thenReturn(Arrays.asList(productDTO,productDTO_2));

        assertTrue(productService.getAll().size() == 2,"The lists should contain the product and product_2");
        assertFalse(productService.getAll().isEmpty(),"The lists should contain two products");
    }

    @Test
    public void givenIdProduct_whenGetById_thenProductNotBeNull() {
        when(productService.getById(product.getIdProduct())).thenReturn(Optional.ofNullable(productDetailDTO));

        assertNotNull(productController.getById(product.getIdProduct()),"The method should return user");
    }

    @Test
    public void givenExistingProduct_whenCreate_thenReturnThrowException() {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        ResponseException responseException = new ResponseException("Product already exist", null, HttpStatus.BAD_REQUEST);

        when(productService.createProduct(productCreateDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productController.createProduct(productCreateDTO), "The method should return ResponseException");

        assertEquals("Product already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProduct_whenAddToDatabase_thenItIsPersisted() {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setName("Test Name");

        when(productService.createProduct(productCreateDTO)).thenReturn(productCreateDTO);

        ProductCreateDTO savedProduct = productController.createProduct(productCreateDTO);

        assertNotNull(savedProduct, "The saved object should not be null");
        assertEquals("Test Name", savedProduct.getName(), "The object name should 'Test Name'");

        verify(productService, times(1)).createProduct(productCreateDTO);
    }

    @Test
    public void givenInvalidProductId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        productDetailDTO = new ProductDetailDTO();
        ResponseException responseException = new ResponseException("That product does not exist", null, HttpStatus.BAD_REQUEST);

        when(productService.updateProduct(idInvalid, productDetailDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productController.updateProduct(idInvalid,productDetailDTO),"The method should return ResponseException");

        assertEquals("That product does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProduct_whenUpdate_thenReturnOk() {
        Product product_2 = new Product();
        product_2.setIdProduct(2L);
        List<ProductAttributeDTO> productAttributeDTOList = new ArrayList<>();

        ProductDetailDTO productDetailDTO_2 = new ProductDetailDTO(2L,"Test 2 Name",5000.4,5,
                "Test 2 Description",true,categoryDTO,userDTO,productAttributeDTOList);

        when(productService.updateProduct(product_2.getIdProduct(),productDetailDTO_2)).thenReturn(
                new ProductDetailDTO(product_2.getIdProduct(),productDetailDTO_2.getName(),productDetailDTO_2.getPrice(),
                        productDetailDTO_2.getStock(), productDetailDTO_2.getDescription(),productDetailDTO_2.isWeeklyOffer(),
                        productDetailDTO_2.getCategory(), productDetailDTO_2.getUser(), productDetailDTO_2.getProductAttributes())
        );

        ProductDetailDTO productUpdated = productController.updateProduct(product_2.getIdProduct(),productDetailDTO_2);

        assertEquals(productUpdated.getName(),productDetailDTO_2.getName(), "The name should be 'Test 2 name' in both cases");

    }
}
