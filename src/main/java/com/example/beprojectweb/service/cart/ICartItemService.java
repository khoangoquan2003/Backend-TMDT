package com.example.beprojectweb.service.cart;

import com.example.beprojectweb.dto.request.cart.CartItemRequest;
import com.example.beprojectweb.dto.response.cart.CartItemResponse;
import com.example.beprojectweb.entity.CartItem;

import java.util.UUID;

public interface ICartItemService {
    CartItemResponse addProductToCart( CartItemRequest request);
    void removeItemFromCart(long cartItemId);
}
