package com.example.beprojectweb.service.cart;

import com.example.beprojectweb.dto.request.cart.CartItemRequest;
import com.example.beprojectweb.dto.response.cart.CartItemResponse;
import com.example.beprojectweb.entity.Cart;
import com.example.beprojectweb.entity.CartItem;
import com.example.beprojectweb.entity.Product;
import com.example.beprojectweb.entity.User;
import com.example.beprojectweb.mapper.CartItemMapper;
import com.example.beprojectweb.repository.CartItemRepository;
import com.example.beprojectweb.repository.CartRepository;
import com.example.beprojectweb.repository.ProductRepository;
import com.example.beprojectweb.repository.UserRepository;
import com.sun.security.auth.UserPrincipal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService implements ICartItemService {
    UserRepository userRepository;
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;
    ProductRepository productRepository;
    CartItemMapper cartItemMapper;
    private final CartService cartService;

    @Override
    public CartItemResponse addProductToCart(CartItemRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartService.getOrCreateCartForUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartAndProduct(cart, product);

        CartItem cartItem;
        if (existingItemOpt.isPresent()) {
            cartItem = existingItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = cartItemMapper.toCartItem(request);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
        }

        CartItem saved = cartItemRepository.save(cartItem);
        cartService.updateCartTotalAmount(cart);

        return cartItemMapper.toCartItemResponse(saved);
    }

    @Override
    public void removeItemFromCart(long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        Cart cart = cartItem.getCart();

        cartItemRepository.delete(cartItem);
        cartService.updateCartTotalAmount(cart);
    }


}
