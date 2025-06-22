package com.example.beprojectweb.entity;

import com.example.beprojectweb.enums.OrderCustomStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCustom {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    User user;

    String designFileUrl; // link file thiết kế
    BigDecimal quotedPrice; // báo giá (nullable lúc đầu)
    int quantity;

    @Enumerated(EnumType.STRING)
    OrderCustomStatus status;

    LocalDateTime createdAt;
}
