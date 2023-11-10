package com.mercado_liebre.product_service.model.productAttribute;

import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductAttributeMapper {

    ProductAttributeMapper mapper = Mappers.getMapper(ProductAttributeMapper.class);

    ProductAttributeDTO productAttributeToProductAttributeDto(ProductAttribute productAttribute);
    ProductAttribute productAttributeDtoToProductAttribute(ProductAttributeDTO productAttributeDTO);
}
