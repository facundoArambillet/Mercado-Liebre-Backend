package com.mercado_liebre.user_service.model.userAddress;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDTO {
    private Long idAddress;
    private String address;
    private int addressNumber;
    private String province;
    private String city;
}
