package com.example.beprojectweb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_ID", referencedColumnName = "cate_ID")
    @JsonBackReference
    Category category;

    @Column(nullable = false)
    private String productName;

    @Column
    String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    int stock;

    @Column(nullable = true)
    String imgUrl;

}
