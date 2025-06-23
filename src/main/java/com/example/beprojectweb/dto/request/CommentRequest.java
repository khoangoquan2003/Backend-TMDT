package com.example.beprojectweb.dto.request;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    UUID productId;
    String content;
}