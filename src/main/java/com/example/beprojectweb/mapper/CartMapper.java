package com.example.beprojectweb.mapper;

import com.example.beprojectweb.dto.response.cart.CartResponse;
import com.example.beprojectweb.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {

    @Mapping(source = "cartItems", target = "cartItems") // dùng CartItemMapper
    CartResponse toCartResponse(Cart cart);
}
