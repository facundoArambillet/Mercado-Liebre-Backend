package com.mercado_liebre.product_service.model.paymentPlant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPlanDTO {

    private Long idPayment;
    private int installments;
    private double interest;
}
