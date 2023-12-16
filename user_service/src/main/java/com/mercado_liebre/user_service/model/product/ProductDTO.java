package com.mercado_liebre.user_service.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long idProduct;
    private String name;
    private double price;
    private int stock;
    private String description;
    private boolean weeklyOffer;

}
