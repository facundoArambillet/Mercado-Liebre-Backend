package com.mercado_liebre.user_service.model.rolAttribute;

import com.mercado_liebre.user_service.model.userRol.UserRolDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolAttributeDTO {

    private String name;
    private String value;
    private UserRolDTO userRol;
}
