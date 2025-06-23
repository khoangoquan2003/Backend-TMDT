package com.example.beprojectweb.repository;


import com.example.beprojectweb.entity.Comment;
import com.example.beprojectweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByProduct(Product product);
}
