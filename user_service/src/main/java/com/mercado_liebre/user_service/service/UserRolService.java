package com.mercado_liebre.user_service.service;


import com.mercado_liebre.user_service.model.userRol.UserRol;
import com.mercado_liebre.user_service.model.userRol.UserRolDTO;

import java.util.List;
import java.util.Optional;

public interface UserRolService {
    List<UserRolDTO> getAll();
    Optional<UserRolDTO> getById(Long id);
    Optional<UserRol> getByType(String type);
    UserRolDTO createUserRol(UserRolDTO userRolDTO);
    UserRolDTO updateUserRol(Long id, UserRolDTO userRol);
    UserRolDTO deleteUserRol(Long id);
}
