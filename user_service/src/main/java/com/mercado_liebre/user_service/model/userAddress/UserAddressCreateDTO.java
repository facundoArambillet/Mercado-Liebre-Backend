package com.mercado_liebre.user_service.model.userAddress;

import com.mercado_liebre.user_service.model.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressCreateDTO {

    private String address;
    private int addressNumber;
    private String province;
    private String city;
    private int postalCode;
    private int contactPhone;
    private UserDTO user;
}
