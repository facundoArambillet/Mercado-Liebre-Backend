package com.mercado_liebre.product_service.model.categoryAttribute;

import com.mercado_liebre.product_service.model.category.Category;
import com.mercado_liebre.product_service.model.category.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryAttributeMapper {

    CategoryAttributeMapper mapper = Mappers.getMapper(CategoryAttributeMapper.class);

    CategoryAttributeDTO categoryAttributeToCategoryAttributeDto(CategoryAttribute categoryAttribute);
    CategoryAttribute categoryAttributeDtoToCategoryAttribute(CategoryAttributeDTO categoryAttributeDTO);
}

