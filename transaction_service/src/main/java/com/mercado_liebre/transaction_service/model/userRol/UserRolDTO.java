package com.mercado_liebre.transaction_service.model.userRol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolDTO {
    private Long idRol;
    private String type;
}
