package com.mercado_liebre.transaction_service.service;

import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlan;
import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlanDTO;

import java.util.List;
import java.util.Optional;

public interface PaymentPlanService {
    List<PaymentPlanDTO> getAll();
    Optional<PaymentPlan> getById(Long id);
    PaymentPlan createPaymentPlan(PaymentPlan paymentPlan);
    PaymentPlanDTO updatePaymentPlan(Long id, PaymentPlanDTO paymentPlanDTO);
    PaymentPlanDTO deletePaymentPlan(Long id);
}
