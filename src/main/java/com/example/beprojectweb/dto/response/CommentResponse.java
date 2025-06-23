package com.example.beprojectweb.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private String id;
    private String content;
    private LocalDateTime createdAt;

    private UserInfo user;

    @Data
    @Builder
    public static class UserInfo {
        private String fullName;
        private String avatarUrl;
    }
}

