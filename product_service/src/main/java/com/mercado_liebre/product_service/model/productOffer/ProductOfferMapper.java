package com.mercado_liebre.product_service.model.productOffer;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductOfferMapper {

    ProductOfferMapper mapper = Mappers.getMapper(ProductOfferMapper.class);

    ProductOfferDTO productOfferToProductOfferDto(ProductOffer productOffer);
    ProductOffer productOfferDtoToProductOffer(ProductOfferDTO productOfferDTO);
}
