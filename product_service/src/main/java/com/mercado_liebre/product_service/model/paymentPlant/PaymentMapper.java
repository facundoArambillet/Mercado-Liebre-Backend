package com.mercado_liebre.product_service.model.paymentPlant;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);

    PaymentPlanDTO paymentPlanToPaymentPlanDto(PaymentPlan paymentPlan);
    PaymentPlan paymentPlanDtoToPaymentPlan(PaymentPlanDTO paymentPlanDTO);
}
