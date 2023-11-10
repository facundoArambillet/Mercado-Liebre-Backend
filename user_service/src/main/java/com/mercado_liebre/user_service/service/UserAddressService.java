package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.model.userAddress.UserAddressCreateDTO;
import com.mercado_liebre.user_service.model.userAddress.UserAddressDTO;
import com.mercado_liebre.user_service.model.userAddress.UserAddressDetailDTO;

import java.util.List;
import java.util.Optional;

public interface UserAddressService {
    List<UserAddressDTO> getAll();
    Optional<UserAddressDetailDTO> getById(Long id);
    UserAddressCreateDTO createUserAddress(UserAddressCreateDTO userAddressCreateDTO);
    UserAddressDetailDTO updateUserAddress(Long id, UserAddressDetailDTO userAddressDetailDTO);
    UserAddressDTO deleteUserAddress(Long id);
}
