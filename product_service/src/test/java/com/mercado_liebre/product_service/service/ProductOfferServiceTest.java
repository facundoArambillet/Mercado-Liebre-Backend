package com.mercado_liebre.product_service.service;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.productOffer.ProductOffer;
import com.mercado_liebre.product_service.model.productOffer.ProductOfferDTO;
import com.mercado_liebre.product_service.repository.ProductOfferRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class ProductOfferServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductOfferRepository productOfferRepository;
    @InjectMocks
    private ProductOfferServiceImpl productOfferService;

    @Mock
    private ProductOffer productOffer;
    @Mock
    private Product product;
    private ProductOfferDTO productOfferDTO;
    private ProductOfferDTO productOfferDTO_2;

    @Test
    public void givenProductOffer_whenGetAll_thenListShouldNotBeEmpty() {
        ProductOffer productOffer_2 = new ProductOffer();

        when(productOfferRepository.findAll()).thenReturn(Arrays.asList(productOffer,productOffer_2));

        assertTrue(productOfferService.getAll().size() == 2,"The lists should contain the productOffer and productOffer_2");
        assertFalse(productOfferService.getAll().isEmpty(),"The lists should contain two productOffers");
    }

    @Test
    public void givenIdProductOffer_whenGetById_thenProductOfferNotBeNull() {
        when(productOfferRepository.findById(productOffer.getIdProductOffer())).thenReturn(Optional.ofNullable(productOffer));

        assertNotNull(productOfferService.getById(productOffer.getIdProductOffer()),"The method should return productOffer");
    }

    @Test
    public void givenExistingProductOffer_whenCreate_thenReturnThrowException() {
        Product product_2 = new Product();
        product_2.setIdProduct(2L);
        ProductOffer productOffer_2 = new ProductOffer();
        productOffer_2.setProduct(product_2);
        ResponseException responseException = new ResponseException("Product Offer already exist", null, HttpStatus.BAD_REQUEST);

        when(productOfferRepository.findByIdProduct(productOffer_2.getProduct().getIdProduct()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productOfferService.createProductOffer(productOffer_2), "The method should return ResponseException");
        System.out.println(exceptionCaptured);
        assertEquals("Product Offer already exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenInvalidProductOfferId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That product offer does not exist", null, HttpStatus.BAD_REQUEST);

        when(productOfferRepository.findById(idInvalid))
                .thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productOfferService.updateProductOffer(idInvalid,productOfferDTO),"The method should return ResponseException");

        assertEquals("That product offer does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProductOffer_whenUpdate_thenReturnOk() {
        Product product_2 = new Product();
        product_2.setIdProduct(2L);
        ProductOffer productOffer_2 = new ProductOffer();
        productOffer_2.setIdProductOffer(2L);
        productOffer_2.setTotal(5500.0);
        productOfferDTO_2 = new ProductOfferDTO();
        productOfferDTO_2.setProduct(product_2);

        when(productOfferRepository.save(productOffer_2)).thenReturn(productOffer_2);
        when(productOfferRepository.findById(productOffer_2.getIdProductOffer())).thenReturn(Optional.ofNullable(productOffer_2));
        when(productRepository.findById(product_2.getIdProduct())).thenReturn(Optional.ofNullable(product_2));

        ProductOfferDTO productOfferDtoUpdated = productOfferService.updateProductOffer(product_2.getIdProduct(),productOfferDTO_2);

        assertEquals(productOfferDtoUpdated.getTotal(),productOfferDTO_2.getTotal(), "The total should be '3500.0' in both cases");
    }

    @Test
    public void givenProductWithNotExist_whenCreateProductOffer_thenReturnThrowException() {
        Product product_2 = new Product();
        product_2.setIdProduct(2L);
        ProductOffer productOffer_2 = new ProductOffer();
        productOffer_2.setIdProductOffer(2L);
        productOffer_2.setProduct(product_2);
        ResponseException responseException = new ResponseException("That product does not exist", null, HttpStatus.BAD_REQUEST);

        when(productRepository.findById(product_2.getIdProduct()))
                .thenThrow(responseException);
        ResponseException exceptionCaptured =  assertThrows(ResponseException.class, () ->
                productOfferService.createProductOffer(productOffer_2), "The method should return ResponseException");

        verify(productOfferRepository, never()).save(any());

        assertEquals("That product does not exist",exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST,exceptionCaptured.getHttpStatus());
    }
}
