package com.example.beprojectweb.repository;


import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    boolean existsProductByProductName(String productName);

    int countByCategory(Category category);

    Optional<Product> findByProductName(String productName);


}
