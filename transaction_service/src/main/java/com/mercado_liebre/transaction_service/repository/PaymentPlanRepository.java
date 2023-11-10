package com.mercado_liebre.transaction_service.repository;

import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan,Long> {
    Optional<PaymentPlan> findByInstallments(Integer installment);
}
