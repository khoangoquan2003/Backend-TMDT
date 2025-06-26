package com.example.beprojectweb.dto.request;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    private UUID productId;
    private String content;
    private int rating;  // thêm trường rating (số nguyên)
}
