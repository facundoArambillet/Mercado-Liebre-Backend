package com.mercado_liebre.product_service.model.productImage;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductImageMapper {
    ProductImageMapper mapper = Mappers.getMapper(ProductImageMapper.class);

    ProductImageDTO productImageToProductImageDto(ProductImage productImage);
    ProductImage productImageDtoToProductImage(ProductImageDTO productImageDTO);
}
