package com.example.beprojectweb.repository;


import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    boolean existsProductByProductName(String productName);

    int countByCategory(Category category);

    Optional<Product> findByProductName(String productName);

    List<Product> findByCategory(Category category);

    List<Product> findTop6ByOrderByCreatedAtDesc(Pageable pageable);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
