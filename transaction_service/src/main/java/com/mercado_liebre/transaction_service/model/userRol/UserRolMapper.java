package com.mercado_liebre.transaction_service.model.userRol;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRolMapper {

    UserRolMapper mapper = Mappers.getMapper(UserRolMapper.class);

    UserRolDTO userRolToUserRolDto(UserRol userRol);
    UserRol userRolDtoToUserRol(UserRolDTO UserRol);
}
