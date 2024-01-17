package com.aozbek.ecommerce.mapper;

import com.aozbek.ecommerce.dto.UpdatedProductDto;
import com.aozbek.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdatedProductMapper {
    @Mapping(target = "id", source = "updatedProductDto.id")
    @Mapping(target = "productName", source = "updatedProductDto.productName")
    @Mapping(target = "price", source = "updatedProductDto.price")
    @Mapping(target = "description", source = "updatedProductDto.description")
    @Mapping(target = "category.id", source = "updatedProductDto.categoryId")
    Product map(UpdatedProductDto updatedProductDto);
}
