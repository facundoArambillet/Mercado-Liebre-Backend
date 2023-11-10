package com.mercado_liebre.transaction_service.model.user;

import com.mercado_liebre.transaction_service.model.product.Product;
import com.mercado_liebre.transaction_service.model.userRol.UserRolDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    private Long idUser;
    private String email;
    private String name;
    private String lastName;
    private Date creationDate;
    private Long salesMade;
    private UserRolDTO userRol;
    private Set<Product> products;
}
