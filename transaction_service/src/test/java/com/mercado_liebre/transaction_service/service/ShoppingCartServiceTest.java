//package com.mercado_liebre.transaction_service.service;
//
//import com.mercado_liebre.transaction_service.error.ResponseException;
//import com.mercado_liebre.transaction_service.model.product.Product;
//import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
//import com.mercado_liebre.transaction_service.model.user.User;
//import com.mercado_liebre.transaction_service.repository.ShoppingCartRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ShoppingCartServiceTest {
//    @Mock
//    private ShoppingCartRepository shoppingCartRepository;
//    @InjectMocks
//    private ShoppingCartServiceImpl shoppingCartService;
//    @Mock
//    private ShoppingCart shoppingCart;
//    @Mock
//    private User user;
//
//
//    @Test
//    public void givenShoppingCarts_whenGetAll_thenListShouldNotBeEmpty() {
//        ShoppingCart shoppingCart_2 = new ShoppingCart();
//
//        when(shoppingCartRepository.findAll()).thenReturn(Arrays.asList(shoppingCart,shoppingCart_2));
//
//        assertTrue(shoppingCartService.getAll().size() == 2,"The lists should contain the shoppingCart and shoppingCart_2");
//        assertFalse(shoppingCartService.getAll().isEmpty(),"The lists should contain two shoppingCarts");
//    }
//
//    @Test
//    public void givenIdShoppingCart_whenGetById_thenShoppingCartNotBeNull() {
//        when(shoppingCartRepository.findById(shoppingCart.getIdCart())).thenReturn(Optional.ofNullable(shoppingCart));
//
//        assertNotNull(shoppingCartService.getById(shoppingCart.getIdCart()),"The method should return shopping cart");
//    }
//
//    @Test
//    public void givenUserWithNotExist_whenCreateShoppingCart_thenReturnThrowException() {
//        List<Product> products = new ArrayList<>();
//        User user_2 = new User();
//        user_2.setIdUser(2L);
//        ShoppingCart shoppingCart_2 = new ShoppingCart(2L,5000.0,user_2,products);
//        ResponseException responseException = new ResponseException("User does not exist", null , HttpStatus.BAD_REQUEST);
//
//        when(shoppingCartRepository.findByIdUser(shoppingCart_2.getUser().getIdUser())).thenThrow(responseException);
//        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () -> shoppingCartService.createShoppingCart(shoppingCart_2), "The method should return ResponseException");
//
//        verify(shoppingCartRepository, never()).save(any());
//
//        assertEquals("User does not exist", exceptionCaptured.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
//    }
//}
