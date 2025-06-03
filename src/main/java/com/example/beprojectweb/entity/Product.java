package com.example.beprojectweb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;

    @ManyToOne
    @JoinColumn(name = "cate_ID", referencedColumnName = "cate_ID")
    @JsonBackReference
    Category category;
    String productName;
    String description;
    BigDecimal price;
    int stock;
    String img;



}
