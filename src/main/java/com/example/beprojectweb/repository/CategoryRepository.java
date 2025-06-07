package com.example.beprojectweb.repository;

import com.example.beprojectweb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsCategoriesByName(String name);
}
