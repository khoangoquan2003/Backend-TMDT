package com.example.beprojectweb.repository;


import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsProductByProductName(String productName);

    int countByCategory(Category category);

    Optional<Product> findByProductName(String productName);

}
