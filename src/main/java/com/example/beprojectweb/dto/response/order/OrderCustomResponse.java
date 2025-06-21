package com.example.beprojectweb.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCustomResponse {
    private UUID id;
    private Integer quantity;
    private List<String> designFileUrls;
    private String status;
    private LocalDateTime createdAt;
    private BigDecimal quotedPrice;
}
