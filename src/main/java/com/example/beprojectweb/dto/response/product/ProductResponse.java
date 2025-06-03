package com.example.beprojectweb.dto.response.product;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long productId;
    String categoryName;
    String productName;
    String description;
    BigDecimal price;
    int stock;
    String img;

}
