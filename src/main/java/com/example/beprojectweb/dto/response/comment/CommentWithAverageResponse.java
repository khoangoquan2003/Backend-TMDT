package com.example.beprojectweb.dto.response.comment;

import com.example.beprojectweb.dto.response.CommentResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentWithAverageResponse {
    private double averageRating;
    private List<CommentResponse> comments;
}
