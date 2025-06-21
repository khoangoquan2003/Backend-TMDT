package com.example.beprojectweb.dto.response.category;

import com.example.beprojectweb.dto.response.product.ProductResponse;
import com.example.beprojectweb.entity.Product;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    UUID cate_ID;
    String name;
    String description;
    String urlImage;
    int count;
    List<ProductResponse> productList;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
