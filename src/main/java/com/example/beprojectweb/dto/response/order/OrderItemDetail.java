package com.example.beprojectweb.dto.response.order;

import com.example.beprojectweb.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDetail {
    private UUID id;
    private UUID productId;
    private String productName;
    private String productImg;
    private String category;
    private Integer quantity;
    private Double itemPrice;
}
