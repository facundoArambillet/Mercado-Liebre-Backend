package com.mercado_liebre.user_service.service;

import com.mercado_liebre.user_service.model.rolAttribute.RolAttribute;
import com.mercado_liebre.user_service.model.rolAttribute.RolAttributeDTO;

import java.util.List;
import java.util.Optional;

public interface RolAttributeService {
    List<RolAttributeDTO> getAll();
    Optional<RolAttribute> getById(Long id);
    Optional<RolAttribute> getByName(String type);
    RolAttribute createRolAttribute(RolAttribute rolAttribute);
    RolAttributeDTO updateRolAttribute(Long id, RolAttributeDTO rolAttributeDTO);
    RolAttributeDTO deleteRolAttribute(Long id);
}
