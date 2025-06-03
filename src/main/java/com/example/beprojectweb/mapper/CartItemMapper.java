package com.example.beprojectweb.mapper;

import com.example.beprojectweb.dto.request.cart.CartItemRequest;
import com.example.beprojectweb.dto.response.cart.CartItemResponse;
import com.example.beprojectweb.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring" , uses = {ProductMapper.class})
public interface CartItemMapper {

    CartItem toCartItem(CartItemRequest request);

    @Mapping(source = "product", target = "product")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    void updateEntity(@MappingTarget CartItem cartItem, CartItemRequest request);
}
