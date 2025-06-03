package com.example.beprojectweb.dto.response.cart;

import com.example.beprojectweb.dto.response.product.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    Long id;
    int quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;
    ProductResponse product;
}