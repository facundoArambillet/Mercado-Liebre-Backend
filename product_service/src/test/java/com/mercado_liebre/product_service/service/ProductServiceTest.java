package com.mercado_liebre.product_service.service;


import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.product.*;
import com.mercado_liebre.product_service.model.user.User;
import com.mercado_liebre.product_service.model.user.UserDTO;
import com.mercado_liebre.product_service.model.user.UserDetailDTO;
import com.mercado_liebre.product_service.repository.CategoryRepository;
import com.mercado_liebre.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private Product product;
    @Mock
    private Category category;
    private UserDTO userDTO;
    private CategoryDTO categoryDTO;
    private ProductDTO productDTO;

    @Test
    public void givenProducts_whenGetAll_thenListShouldNotBeEmpty() {
        Product product_2 = new Product();

        when(productRepository.findAll()).thenReturn(Arrays.asList(product,product_2));

        assertTrue(productService.getAll().size() == 2,"The lists should contain the product and product_2");
        assertFalse(productService.getAll().isEmpty(),"The lists should contain two products");
    }

    @Test
    public void givenIdProduct_whenGetById_thenProductNotBeNull() {
        when(productRepository.findById(product.getIdProduct())).thenReturn(Optional.ofNullable(product));

        assertNotNull(productService.getById(product.getIdProduct()),"The method should return product");
    }

    @Test
    public void givenExistingProduct_whenCreate_thenReturnThrowException() {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        Product product_2 = new Product();
        product_2.setIdProduct(2L);
        productCreateDTO.setName("Test Name");
        ResponseException responseException = new ResponseException("Product already exist", null, HttpStatus.BAD_REQUEST);

        when(productRepository.findByName(productCreateDTO.getName()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productService.createProduct(productCreateDTO), "The method should return ResponseException");

        assertEquals("Product already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidProductId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        ResponseException responseException = new ResponseException("That product does not exist", null, HttpStatus.BAD_REQUEST);

        when(productRepository.findById(idInvalid)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productService.updateProduct(idInvalid,productDetailDTO),"The method should return ResponseException");

        assertEquals("That product does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProduct_whenUpdate_thenReturnOk() {
        Product product_2 = new Product();
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDTO = new UserDTO();
        categoryDTO = new CategoryDTO();
        product_2.setIdProduct(2L);
        productDetailDTO.setName("Test 2 Name");
        productDetailDTO.setUser(userDTO);
        productDetailDTO.setCategory(categoryDTO);
        userDTO.setIdUser(1L);
        categoryDTO.setIdCategory(1L);

        when(productRepository.save(product_2)).thenReturn(product_2);
        when(productRepository.findById(product_2.getIdProduct())).thenReturn(Optional.ofNullable(product_2));
        when(restTemplate.getForObject("http://user-service/user/" + userDTO.getIdUser() , UserDetailDTO.class))
                .thenReturn(userDetailDTO);
        when(categoryRepository.findById(categoryDTO.getIdCategory())).thenReturn(Optional.of(category));

        ProductDetailDTO productUpdated = productService.updateProduct(product_2.getIdProduct(),productDetailDTO);

        assertEquals(productUpdated.getName(),productDetailDTO.getName(), "The name should be 'Test 2 Name' in both cases");
    }

    @Test
    public void givenUserWithNotExist_whenCreateProduct_thenReturnThrowException() {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        userDTO = new UserDTO();
        userDTO.setIdUser(1L);
        productCreateDTO.setUser(userDTO);
        ResponseException responseException = new ResponseException("User does not exist", null, HttpStatus.BAD_REQUEST);

        when(restTemplate.getForObject("http://user-service/user/" + userDTO.getIdUser() , UserDetailDTO.class))
                .thenThrow(responseException);
        ResponseException exceptionCaptured =  assertThrows(ResponseException.class, () ->
                productService.createProduct(productCreateDTO), "The method should return ResponseException");

        verify(productRepository, never()).save(any());

        assertEquals("User does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenCategoryWithNotExist_whenCreateProduct_thenReturnThrowException() {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        categoryDTO = new CategoryDTO();
        categoryDTO.setIdCategory(1L);
        userDTO = new UserDTO();
        userDTO.setIdUser(1L);
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setIdUser(1L);
        productCreateDTO.setCategory(categoryDTO);
        productCreateDTO.setUser(userDTO);

        ResponseException responseException = new ResponseException("Category does not exist", null, HttpStatus.BAD_REQUEST);

        when(restTemplate.getForObject("http://user-service/user/" + userDTO.getIdUser() , UserDetailDTO.class))
                .thenReturn(userDetailDTO);
        when(categoryRepository.findById(categoryDTO.getIdCategory()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productService.createProduct(productCreateDTO), "The method should return ResponseException");

        // Verifica que no se haya intentado guardar el usuario en el productRepository
        verify(productRepository, never()).save(any());

        assertEquals("Category does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }
}
