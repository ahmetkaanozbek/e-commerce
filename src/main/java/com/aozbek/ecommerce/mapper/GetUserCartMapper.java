package com.aozbek.ecommerce.mapper;

import com.aozbek.ecommerce.dto.GetUserCartDto;
import com.aozbek.ecommerce.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GetUserCartMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "product", source = "product")
    GetUserCartDto map(CartItem cartItem);

    List<GetUserCartDto> map(List<CartItem> cartItems);
}
