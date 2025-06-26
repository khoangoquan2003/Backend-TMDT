package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.CommentRequest;
import com.example.beprojectweb.dto.response.CommentResponse;
import com.example.beprojectweb.dto.response.comment.CommentWithAverageResponse;
import com.example.beprojectweb.entity.Comment;
import com.example.beprojectweb.entity.User;
import com.example.beprojectweb.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody CommentRequest request, Principal principal) {
        Comment comment = commentService.addComment(request, principal);
        return ResponseEntity.ok(toResponse(comment));
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<CommentWithAverageResponse> getByProduct(@PathVariable UUID productId) {
        List<Comment> comments = commentService.getCommentsByProduct(productId);

        List<CommentResponse> responseList = comments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        // Tính điểm trung bình, bỏ qua comment không có rating (null)
        double averageRating = comments.stream()
                .filter(c -> c.getRating() != null)
                .mapToInt(Comment::getRating)
                .average()
                .orElse(0.0);

        CommentWithAverageResponse response = CommentWithAverageResponse.builder()
                .averageRating(averageRating)
                .comments(responseList)
                .build();

        return ResponseEntity.ok(response);
    }


    private CommentResponse toResponse(Comment comment) {
        User user = comment.getUser();

        return CommentResponse.builder()
                .id(comment.getId().toString())
                .content(comment.getContent())
                .rating(comment.getRating() != null ? comment.getRating() : 0)
                .createdAt(comment.getCreatedAt())
                .user(CommentResponse.UserInfo.builder()
                        .fullName(user.getFullName())
                        .avatarUrl(user.getAvatarUrl())
                        .build())
                .build();
    }


}
