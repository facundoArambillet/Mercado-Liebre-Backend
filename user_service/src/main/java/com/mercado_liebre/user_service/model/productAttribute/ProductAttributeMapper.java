package com.mercado_liebre.user_service.model.productAttribute;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductAttributeMapper {

    ProductAttributeMapper mapper = Mappers.getMapper(ProductAttributeMapper.class);

    ProductAttributeDTO productAttributeToProductAttributeDto(ProductAttribute productAttribute);
    ProductAttribute productAttributeDtoToProductAttribute(ProductAttributeDTO productAttributeDTO);
}
