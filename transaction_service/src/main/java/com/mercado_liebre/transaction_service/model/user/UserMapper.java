package com.mercado_liebre.transaction_service.model.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDto(User user);
    UserDetailDTO userToUserDetailDto(User user);
    UserCreateDTO userToUserCreationDto(User user);

    User userDtoToUser(UserDTO userDTO);
    User userDetailDtoToUser(UserDetailDTO userDetailDTO);
    User userCreationDtoToUser(UserCreateDTO userCreateDTO);

}
