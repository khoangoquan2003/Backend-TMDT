package com.example.beprojectweb.entity;

import com.example.beprojectweb.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends AbstractEntity{
    @Id
    @GeneratedValue
    UUID product_ID;

    @ManyToOne
    @JoinColumn(name = "cate_ID", nullable = false)
    @JsonBackReference
    Category category;

    String productName;
    String description;
    BigDecimal price;
    int stock;
    String urlImage;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    ProductStatus status;

}
