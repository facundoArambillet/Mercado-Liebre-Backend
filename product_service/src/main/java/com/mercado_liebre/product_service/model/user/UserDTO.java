package com.mercado_liebre.product_service.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long idUser;
    private String email;
    private String name;
    private String lastName;
    private Long salesMade;

}
