//package com.mercado_liebre.transaction_service.service;
//
//import com.mercado_liebre.transaction_service.error.ResponseException;
//import com.mercado_liebre.transaction_service.model.invoice.Invoice;
//import com.mercado_liebre.transaction_service.model.invoice.InvoiceDTO;
//import com.mercado_liebre.transaction_service.model.shoppingCart.ShoppingCart;
//import com.mercado_liebre.transaction_service.repository.InvoiceRepository;
//import com.mercado_liebre.transaction_service.repository.ShoppingCartRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.sql.Date;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class InvoiceServiceTest {
//    @Mock
//    private InvoiceRepository invoiceRepository;
//    @Mock
//    private ShoppingCartRepository shoppingCartRepository;
//    @InjectMocks
//    private InvoiceServiceImpl invoiceService;
//    @Mock
//    private Invoice invoice;
//    @Mock
//    private ShoppingCart shoppingCart;
//    private InvoiceDTO invoiceDTO;
//
//    @Test
//    public void givenInvoices_whenGetAll_thenListShouldNotBeEmpty() {
//        Invoice invoice_2 = new Invoice();
//
//        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(invoice,invoice_2));
//
//        assertTrue(invoiceService.getAll().size() == 2,"The lists should contain the invoice and invoice_2");
//        assertFalse(invoiceService.getAll().isEmpty(),"The lists should contain two invoices");
//    }
//
//    @Test
//    public void givenIdInvoice_whenGetById_thenInvoiceNotBeNull() {
//        when(invoiceRepository.findById(invoice.getIdInvoice())).thenReturn(Optional.ofNullable(invoice));
//
//        assertNotNull(invoiceService.getById(invoice.getIdInvoice()),"The method should return invoice");
//    }
//
//    @Test
//    public void givenInvalidInvoiceId_whenUpdate_thenThrowException() {
//        Long idInvalid = 2L;
//        invoiceDTO = new InvoiceDTO();
//        ResponseException responseException = new ResponseException("That Invoice does not exist", null, HttpStatus.NOT_FOUND);
//
//        when(invoiceRepository.findById(idInvalid)).thenThrow(responseException);
//        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () -> invoiceService.updateInvoice(idInvalid,invoiceDTO),"The method should return ResponseException");
//
//        assertEquals("That Invoice does not exist",exceptionCaptured.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND,exceptionCaptured.getHttpStatus());
//    }
//
//    @Test
//    public void givenInvoice_whenUpdate_thenReturnOk() {
//        Date date = new Date(System.currentTimeMillis());
//        ShoppingCart shoppingCart_2 = new ShoppingCart();
//        Invoice invoice_2 = new Invoice(2L, 15000.0, date, shoppingCart_2);
//        invoiceDTO = new InvoiceDTO(1L, 5000.0, date, shoppingCart_2);
//        shoppingCart_2.setIdCart(1L);
//
//        when(invoiceRepository.save(invoice_2)).thenReturn(invoice_2);
//        when(invoiceRepository.findById(invoice_2.getIdInvoice())).thenReturn(Optional.ofNullable(invoice_2));
//        when(shoppingCartRepository.findById(shoppingCart_2.getIdCart())).thenReturn(Optional.of(shoppingCart_2));
//        InvoiceDTO invoiceDtoUpdated = invoiceService.updateInvoice(invoice_2.getIdInvoice(),invoiceDTO);
//
//        assertEquals(invoiceDTO.getTotal(), invoiceDtoUpdated.getTotal(), "The total should be '5000.0' in both cases");
//    }
//
//    @Test
//    public void givenShoppingCartWithNotExist_whenCreateInvoice_thenReturnThrowException() {
//        Date date = new Date(System.currentTimeMillis());
//        ShoppingCart shoppingCart_2 = new ShoppingCart();
//        shoppingCart_2.setIdCart(1L);
//        Invoice invoice_2 = new Invoice(2L, 15000.0, date, shoppingCart_2);
//        ResponseException responseException = new ResponseException("Shopping cart does not exist", null, HttpStatus.BAD_REQUEST);
//
//        when(shoppingCartRepository.findById(shoppingCart_2.getIdCart())).thenThrow(responseException);
//        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () -> invoiceService.createInvoice(invoice_2), "The method should return ResponseException");
//
//        verify(invoiceRepository, never()).save(any());
//
//        assertEquals("Shopping cart does not exist", exceptionCaptured.getMessage());
//        assertEquals(HttpStatus.BAD_REQUEST, exceptionCaptured.getHttpStatus());
//    }
//}
