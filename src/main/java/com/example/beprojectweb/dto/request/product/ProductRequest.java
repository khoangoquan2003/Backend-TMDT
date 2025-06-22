package com.example.beprojectweb.dto.request.product;

import com.example.beprojectweb.enums.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String productName;
    String description;
    BigDecimal price;
    int stock;
    UUID cate_ID;
    String urlImage;
    ProductStatus status;
}
