package com.mercado_liebre.user_service.model.categoryFamily;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryFamilyMapper {

    CategoryFamilyMapper mapper = Mappers.getMapper(CategoryFamilyMapper.class);

    CategoryFamilyDTO categoryFamilyToCategoryFamilyDto(CategoryFamily categoryFamily);
    CategoryFamily categoryFamilyDtoToCategoryFamily(CategoryFamilyDTO categoryFamilyDTO);

}
