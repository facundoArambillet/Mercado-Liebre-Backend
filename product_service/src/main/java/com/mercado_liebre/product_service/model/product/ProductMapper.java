package com.mercado_liebre.product_service.model.product;

import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamily;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyDTO;
import com.mercado_liebre.product_service.model.categoryFamily.CategoryFamilyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDto(Product product);
    ProductDetailDTO productToProductDetailDto(Product product);
    ProductCreateDTO productToProductCreateDto(Product product);

    Product productDtoToProduct(ProductDTO productDTO);
    Product productDetailDtoToProduct(ProductDetailDTO productDetailDTO);
    Product productCreateDtoToProduct(ProductCreateDTO productCreateDTO);
}
