package com.example.beprojectweb.repository;

import com.example.beprojectweb.entity.Cart;
import com.example.beprojectweb.entity.CartItem;
import com.example.beprojectweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    List<CartItem> findAllByCart(Cart cart);
}
