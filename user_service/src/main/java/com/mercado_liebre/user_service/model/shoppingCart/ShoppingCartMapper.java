package com.mercado_liebre.user_service.model.shoppingCart;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShoppingCartMapper {

    ShoppingCartMapper mapper = Mappers.getMapper(ShoppingCartMapper.class);

    ShoppingCartDTO shoppingCartToShoppingCartDto(ShoppingCart shoppingCart);
    ShoppingCart shoppingCartDtoToShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
