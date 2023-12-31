package com.mercado_liebre.transaction_service.model.product;

import com.mercado_liebre.transaction_service.model.category.CategoryDTO;
import com.mercado_liebre.transaction_service.model.user.UserDTO;
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
    private boolean isWeeklyOffer;
    private CategoryDTO category;
    private UserDTO user;

}
