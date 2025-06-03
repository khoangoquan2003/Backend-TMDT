package com.example.beprojectweb.dto.response.cart;

import com.example.beprojectweb.dto.response.UserResponse;
import com.example.beprojectweb.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    Long id;
    BigDecimal totalAmount;
    Set<CartItemResponse> cartItems;
}
