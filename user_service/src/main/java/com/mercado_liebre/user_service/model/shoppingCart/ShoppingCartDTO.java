package com.mercado_liebre.user_service.model.shoppingCart;

import com.mercado_liebre.user_service.model.product.ProductDTO;
import com.mercado_liebre.user_service.model.user.UserDTO;
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
    private UserDTO user;
    private List<ProductDTO> products;
}
