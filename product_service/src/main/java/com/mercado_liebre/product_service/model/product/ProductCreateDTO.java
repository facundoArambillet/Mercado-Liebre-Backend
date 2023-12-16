package com.mercado_liebre.product_service.model.product;

import com.mercado_liebre.product_service.model.category.CategoryDTO;
import com.mercado_liebre.product_service.model.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {

    private String name;
    private double price;
    private int stock;
    private String description;
    private boolean weeklyOffer;
    private CategoryDTO category;
    private UserDTO user;

}
