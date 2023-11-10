package com.mercado_liebre.user_service.model.userAddress;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserAddressMapper {

    UserAddressMapper mapper = Mappers.getMapper(UserAddressMapper.class);

    UserAddressDTO userAddressToUserAddressDto(UserAddress userAddress);
    UserAddressDetailDTO userAddressToUserAddressDetailDto(UserAddress userAddress);
    UserAddressCreateDTO userAddressToUserAddressCreateDto(UserAddress userAddress);


    UserAddress userAddressDtoToUserAddress(UserAddressDTO userAddressDTO);
    UserAddress userAddressDetailDtoToUserAddress(UserAddressDetailDTO userAddressDetailDTO);
    UserAddress userAddressCreateDtoToUserAddress(UserAddressCreateDTO userAddressCreateDTO);
}
