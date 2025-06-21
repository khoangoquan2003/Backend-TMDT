package com.example.beprojectweb.dto.request.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {
    String productName;
    String description;
    BigDecimal price;
    int stock;
    String urlImage;
    Long cate_ID;
}
