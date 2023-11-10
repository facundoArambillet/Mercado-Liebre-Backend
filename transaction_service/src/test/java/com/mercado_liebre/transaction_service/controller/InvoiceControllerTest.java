//package com.mercado_liebre.transaction_service.controller;
//
//import com.mercado_liebre.transaction_service.error.ResponseException;
//import com.mercado_liebre.transaction_service.model.invoice.Invoice;
//import com.mercado_liebre.transaction_service.model.product.Product;
//import com.mercado_liebre.transaction_service.model.user.User;
//import com.mercado_liebre.transaction_service.model.userRol.UserRol;
//import com.mercado_liebre.transaction_service.service.InvoiceServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class InvoiceControllerTest {
//    @Mock
//    private InvoiceServiceImpl invoiceService;
//    @InjectMocks
//    private InvoiceController invoiceController;
//    private Invoice invoice;
//    private User user;
//    private UserRol userRol;
//    private List<Product> products;
//
//    @BeforeEach
//    void setUp() {
//        this.userRol = new UserRol(1L,"Test Rol");
//        this.user = new User(1L, "Test@Test.com","Test password","Test name","Test last name",
//                new Date(2023,10,21),0L,userRol);
//        this.products = new ArrayList<>();
//        this.invoice = new Invoice(1L,5000.0,new Date(2023,10,21),user,products);
//    }
//
//    @Test
//    public void givenInvoices_whenGetAll_thenListShouldNotBeEmpty() {
//        UserRol userRol_2 = new UserRol(2L,"Test 2 type");
//        User user_2 = new User(2L, "Test2@Test.com","Test 2 password","Test 2 name","Test 2 last name",
//                new Date(2023,10,21),0L,userRol_2);
//        Invoice invoice_2 = new Invoice(2L,5000.0,new Date(2023,10,21),user_2,products);
//
//        when(invoiceService.getAll()).thenReturn(Arrays.asList(invoice,invoice_2));
//
//        assertTrue(invoiceController.getAll().size() == 2,"The lists should contain the invoice and invoice_2");
//        assertFalse(invoiceController.getAll().isEmpty(),"The lists should contain two invoices");
//    }
//
//    @Test
//    public void givenIdInvoice_whenGetById_thenUserNotBeNull() {
//        when(invoiceService.getById(invoice.getIdInvoice())).thenReturn(Optional.ofNullable(invoice));
//
//        assertNotNull(invoiceController.getById(invoice.getIdInvoice()),"The method should return invoice");
//    }
//
//    @Test
//    public void givenExistingInvoice_whenCreate_thenReturnThrowException() {
//        UserRol userRol_2 = new UserRol(2L,"Test 2 type");
//        User user_2 = new User(2L, "Test@Test.com","Test 2 password","Test 2 name","Test 2 last name",
//                new Date(2023,10,21),0L,userRol_2);
//        Invoice invoice_2 = new Invoice(2L,5000.0,new Date(2023,10,21),user_2,products);
//
//        when(invoiceService.createInvoice(invoice_2)).thenThrow(ResponseException.class);
//        assertThrows(ResponseException.class, () -> invoiceController.createInvoice(invoice_2), "The method should return ResponseException");
//    }
//
//    @Test
//    public void givenInvoice_whenAddToDatabase_thenItIsPersisted() {
//        when(invoiceService.createInvoice(invoice)).thenReturn(invoice);
//
//        Invoice savedInvoice = invoiceController.createInvoice(invoice);
//
//        assertNotNull(savedInvoice, "The saved object should not be null");
//        assertEquals(new Date(2023,10,21), savedInvoice.getDate(), "The object date should 'new Date(2023,10,21)'");
//
//        verify(invoiceService, times(1)).createInvoice(invoice);
//    }
//
//    @Test
//    public void givenInvalidInvoiceId_whenUpdate_thenThrowException() {
//        when(invoiceService.updateInvoice(2L,invoice)).thenThrow(ResponseException.class);
//
//        assertThrows(ResponseException.class, () -> invoiceController.updateInvoice(2L,invoice),"The method should return ResponseException");
//    }
//
//    @Test
//    public void givenInvoice_whenUpdate_thenReturnOk() {
//        UserRol userRol_2 = new UserRol(2L,"Test 2 type");
//        User user_2 = new User(2L, "Test2@Test.com","Test 2 password","Test 2 name","Test 2 last name",
//                new Date(2023,10,21),0L,userRol_2);
//        Invoice invoice_2 = new Invoice(2L,5000.0,new Date(2023,10,21),user_2,products);
//
//        when(invoiceService.updateInvoice(invoice.getIdInvoice(),invoice_2)).thenReturn(
//                new Invoice(invoice.getIdInvoice(), invoice_2.getTotal(), invoice_2.getDate(),
//                        invoice_2.getUser(), invoice_2.getProducts())
//        );
//
//        Invoice invoiceUpdated = invoiceController.updateInvoice(invoice.getIdInvoice(),invoice_2);
//
//        assertEquals(invoiceUpdated.getDate(),invoice_2.getDate(), "The date should be '2023-10-21' in both cases");
//
//    }
//}
