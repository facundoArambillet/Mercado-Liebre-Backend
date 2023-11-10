package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.model.user.User;
import com.mercado_liebre.user_service.model.user.UserCreateDTO;
import com.mercado_liebre.user_service.model.user.UserDTO;
import com.mercado_liebre.user_service.model.user.UserDetailDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAll();
    Optional<UserDetailDTO> getById(Long id);
    Optional<UserDetailDTO> getByEmail(String email);
    UserCreateDTO registerUser(UserCreateDTO userCreateDTO);
    UserDTO incrementSalesMade(Long idUser);
    User updateUser(Long id, User user);
    UserDTO deleteUser(Long id);
}
