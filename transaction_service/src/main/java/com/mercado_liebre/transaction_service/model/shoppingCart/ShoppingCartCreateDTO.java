package com.mercado_liebre.transaction_service.model.shoppingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartCreateDTO {
    private Long idCart;
    private double price;
    private Long idUser;
    private List<Long> idProducts;
}
