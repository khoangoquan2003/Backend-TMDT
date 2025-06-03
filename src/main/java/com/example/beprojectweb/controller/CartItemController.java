package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.cart.CartItemRequest;
import com.example.beprojectweb.dto.response.cart.CartItemResponse;
import com.example.beprojectweb.service.cart.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final ICartItemService cartItemService;
    @PostMapping
    public ResponseEntity<CartItemResponse> addProductToCart(
            @RequestBody CartItemRequest request) {
        CartItemResponse response = cartItemService.addProductToCart( request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long id) {
        cartItemService.removeItemFromCart(id);
        return ResponseEntity.noContent().build();
    }
}
