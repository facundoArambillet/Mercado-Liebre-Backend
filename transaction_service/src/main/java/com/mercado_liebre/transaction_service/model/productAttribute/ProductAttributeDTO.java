package com.mercado_liebre.transaction_service.model.productAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeDTO {
    private Long idProduct;
    private String name;
    private String value;
}
