package com.example.beprojectweb.service.cart;

import com.example.beprojectweb.entity.Cart;

import java.math.BigDecimal;
import java.util.UUID;

public interface ICartService {
    Cart getOrCreateCartForUser();
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    void updateCartTotalAmount(Cart cart);
}
