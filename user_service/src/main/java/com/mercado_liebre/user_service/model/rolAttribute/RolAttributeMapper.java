package com.mercado_liebre.user_service.model.rolAttribute;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RolAttributeMapper {

    RolAttributeMapper mapper = Mappers.getMapper(RolAttributeMapper.class);

    RolAttributeDTO rolAttributeToRolAttributeDto(RolAttribute rolAttribute);
    RolAttribute rolAttributeDtoToRolAttribute(RolAttributeDTO rolAttributeDTO);
}
