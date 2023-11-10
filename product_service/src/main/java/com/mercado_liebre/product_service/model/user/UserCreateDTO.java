package com.mercado_liebre.product_service.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {

    private String email;
    private String password;
    private String name;
    private String lastName;

}
