package com.mercado_liebre.transaction_service.controller;

import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlan;
import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlanDTO;
import com.mercado_liebre.transaction_service.service.PaymentPlanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment-plan")
public class PaymentPlanController {
    @Autowired
    private PaymentPlanServiceImpl paymentPlanService;

    @GetMapping
    public List<PaymentPlanDTO> getAll() {
        return paymentPlanService.getAll();
    }
    @GetMapping("/{idPayment}")
    public Optional<PaymentPlan> getById(@PathVariable("idPayment") Long idPayment) {
        return paymentPlanService.getById(idPayment);
    }
    @PostMapping
    public PaymentPlan createPaymentPlan(@RequestBody PaymentPlan paymentPlan) {
        return paymentPlanService.createPaymentPlan(paymentPlan);
    }
    @PutMapping("/{idPayment}")
    public PaymentPlanDTO updatePaymentPlan(@PathVariable("idPayment") Long idPayment, @RequestBody PaymentPlanDTO paymentPlanDTO) {
        return paymentPlanService.updatePaymentPlan(idPayment, paymentPlanDTO);
    }
    @DeleteMapping("/{idPayment}")
    public PaymentPlanDTO deletePaymentPlan(@PathVariable("idPayment") Long idPayment) {
        return paymentPlanService.deletePaymentPlan(idPayment);
    }
}
