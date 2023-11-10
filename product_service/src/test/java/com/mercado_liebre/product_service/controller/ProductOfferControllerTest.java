package com.mercado_liebre.product_service.controller;

import com.mercado_liebre.product_service.error.ResponseException;
import com.mercado_liebre.product_service.model.product.Product;
import com.mercado_liebre.product_service.model.product.ProductDTO;
import com.mercado_liebre.product_service.model.productOffer.ProductOffer;
import com.mercado_liebre.product_service.model.productOffer.ProductOfferDTO;
import com.mercado_liebre.product_service.service.ProductOfferServiceImpl;
import com.mercado_liebre.product_service.service.ProductServiceImpl;
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
public class ProductOfferControllerTest {
    @Mock
    private ProductServiceImpl productService;
    @Mock
    private ProductOfferServiceImpl productOfferService;
    @InjectMocks
    private ProductOfferController productOfferController;

    @Mock
    private ProductOffer productOffer;
    @Mock
    private Product product;

    private ProductOfferDTO productOfferDTO;
    private ProductDTO productDTO;

    @Test
    public void givenProductOffer_whenGetAll_thenListShouldNotBeEmpty() {
        ProductOfferDTO productOfferDTO_2 = new ProductOfferDTO();

        when(productOfferService.getAll()).thenReturn(Arrays.asList(productOfferDTO,productOfferDTO_2));

        assertTrue(productOfferController.getAll().size() == 2,"The lists should contain the productOffer and productOffer_2");
        assertFalse(productOfferController.getAll().isEmpty(),"The lists should contain two productOffers");
    }

    @Test
    public void givenIdProductOffer_whenGetById_thenUserNotBeNull() {
        when(productOfferService.getById(product.getIdProduct())).thenReturn(Optional.ofNullable(productOfferDTO));

        assertNotNull(productOfferController.getById(product.getIdProduct()),"The method should return productOffer");
    }

    @Test
    public void givenExistingProductOffer_whenCreate_thenReturnThrowException() {
        ResponseException responseException = new ResponseException("That product does not exist", null, HttpStatus.BAD_REQUEST);

        when(productOfferService.createProductOffer(productOffer)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productOfferController.createProductOffer(productOffer), "The method should return ResponseException");

        assertEquals("That product does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProductOffer_whenAddToDatabase_thenItIsPersisted() {
        when(productOfferService.createProductOffer(productOffer)).thenReturn(productOffer);

        ProductOffer savedProductOffer = productOfferController.createProductOffer(productOffer);

        assertNotNull(savedProductOffer, "The saved object should not be null");

        // Verifica que el método save del repositorio se haya llamado exactamente una vez con el objeto
        verify(productOfferService, times(1)).createProductOffer(productOffer);
    }

    @Test
    public void givenInvalidProductOfferId_whenUpdate_thenThrowException() {
        Long idInvalid = 2L;
        ResponseException responseException = new ResponseException("That product offer does not exist", null, HttpStatus.BAD_REQUEST);

        when(productOfferService.updateProductOffer(idInvalid,productOfferDTO)).thenThrow(responseException);
        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
                productOfferController.updateProductOffer(idInvalid,productOfferDTO),"The method should return ResponseException");

        assertEquals("That product offer does not exist", exceptionCaptured.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
    }

    @Test
    public void givenProductOffer_whenUpdate_thenReturnOk() {
        ProductOffer productOffer_2 = new ProductOffer();
        productOfferDTO = new ProductOfferDTO(1L,0.0,4500,product);
        productOffer_2.setIdProductOffer(2L);

        when(productOfferService.updateProductOffer(productOffer_2.getIdProductOffer(),productOfferDTO)).thenReturn(
                new ProductOfferDTO(productOffer_2.getIdProductOffer(),productOfferDTO.getDiscountPercentage(),
                        productOfferDTO.getTotal(),productOfferDTO.getProduct())
        );

        ProductOfferDTO productOfferDtoUpdated = productOfferController.updateProductOffer(productOffer_2.getIdProductOffer(),productOfferDTO);

        assertEquals(productOfferDtoUpdated.getTotal(),productOfferDTO.getTotal(), "The total should be '3500.0' in both cases");

    }
}
