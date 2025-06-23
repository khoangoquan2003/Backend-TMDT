package com.example.beprojectweb.service;

import com.example.beprojectweb.dto.request.CommentRequest;
import com.example.beprojectweb.entity.Comment;
import com.example.beprojectweb.entity.Product;
import com.example.beprojectweb.entity.User;
import com.example.beprojectweb.repository.CommentRepository;
import com.example.beprojectweb.repository.ProductRepository;
import com.example.beprojectweb.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment addComment(CommentRequest request, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Người dùng chưa đăng nhập.");
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + username));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + request.getProductId()));

        Comment comment = Comment.builder()
                .user(user)
                .product(product)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));
        return commentRepository.findByProduct(product);
    }
}
