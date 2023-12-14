//package com.mercado_liebre.transaction_service.service;
//
//import com.mercado_liebre.transaction_service.error.ResponseException;
//import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlan;
//import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlanDTO;
//import com.mercado_liebre.transaction_service.repository.PaymentPlanRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class PaymentPlanServiceTest {
//    @Mock
//    private PaymentPlanRepository paymentPlanRepository;
//    @InjectMocks
//    private PaymentPlanServiceImpl paymentPlanService;
//    @Mock
//    private PaymentPlan paymentPlan;
//    private PaymentPlanDTO paymentPlanDTO;
//
//    @Test
//    public void givenPaymentPlans_whenGetAll_thenListShouldNotBeEmpty() {
//        PaymentPlan paymentPlan_2 = new PaymentPlan();
//
//        when(paymentPlanRepository.findAll()).thenReturn(Arrays.asList(paymentPlan,paymentPlan_2));
//
//        assertTrue(paymentPlanService.getAll().size() == 2,"The lists should contain the paymentPlan and paymentPlan_2");
//        assertFalse(paymentPlanService.getAll().isEmpty(),"The lists should contain two paymentPlans");
//    }
//
//    @Test
//    public void givenIdPaymentPlan_whenGetById_thenPaymentPlanNotBeNull() {
//        when(paymentPlanRepository.findById(paymentPlan.getIdPayment())).thenReturn(Optional.ofNullable(paymentPlan));
//
//        assertNotNull(paymentPlanService.getById(paymentPlan.getIdPayment()),"The method should return invoice");
//    }
//
//    @Test
//    public void givenInvalidPaymentPlanId_whenUpdate_thenThrowException() {
//        Long idInvalid = 2L;
//        paymentPlanDTO = new PaymentPlanDTO();
//        ResponseException responseException = new ResponseException("That Payment plan does not exist", null, HttpStatus.NOT_FOUND);
//
//        when(paymentPlanRepository.findById(idInvalid)).thenThrow(responseException);
//        ResponseException exceptionCaptured = assertThrows(ResponseException.class, () ->
//                paymentPlanService.updatePaymentPlan(idInvalid,paymentPlanDTO),"The method should return ResponseException");
//
//        assertEquals("That Payment plan does not exist",exceptionCaptured.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND,exceptionCaptured.getHttpStatus());
//    }
//
//    @Test
//    public void givenPaymentPlan_whenUpdate_thenReturnOk() {
//        PaymentPlan paymentPlan_2 = new PaymentPlan(2L,3,30);
//        paymentPlanDTO = new PaymentPlanDTO(1L,6,60);
//
//        when(paymentPlanRepository.save(paymentPlan_2)).thenReturn(paymentPlan_2);
//        when(paymentPlanRepository.findById(paymentPlan_2.getIdPayment())).thenReturn(Optional.ofNullable(paymentPlan_2));
//        PaymentPlanDTO paymentPlanDtoUpdated = paymentPlanService.updatePaymentPlan(paymentPlan_2.getIdPayment(),paymentPlanDTO);
//
//        assertEquals(paymentPlanDTO.getInstallments(), paymentPlanDtoUpdated.getInstallments(), "The total installments be '6' in both cases");
//    }
//
//}
