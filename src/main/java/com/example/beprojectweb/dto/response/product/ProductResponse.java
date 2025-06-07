package com.example.beprojectweb.dto.response.product;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    UUID productId;
    String categoryName;
    String productName;
    String description;
    BigDecimal price;
    int stock;
    String imgUrl;

}
