package com.mercado_liebre.transaction_service.model.shoppingCart;

import com.mercado_liebre.transaction_service.model.product.ProductDetailDTO;
import com.mercado_liebre.transaction_service.model.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {
    private Long idCart;
    private double price;
    private Long idUser;
    private List<ProductDetailDTO> products;
}
