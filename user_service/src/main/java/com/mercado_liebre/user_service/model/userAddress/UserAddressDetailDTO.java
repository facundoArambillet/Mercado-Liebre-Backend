package com.mercado_liebre.user_service.model.userAddress;

import com.mercado_liebre.user_service.model.user.User;
import com.mercado_liebre.user_service.model.user.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressDetailDTO {
    private Long idAddress;
    private String address;
    private int addressNumber;
    private String province;
    private String city;
    private int postalCode;
    private BigInteger contactPhone;
    private boolean isPrincipal;
    private UserDTO user;
}
