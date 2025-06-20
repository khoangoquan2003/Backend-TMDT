package com.example.beprojectweb.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
//@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends AbstractEntity{
    @Id
    @GeneratedValue
    UUID cate_ID;
    String name;
    String description;
    String urlImage;
    int count;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Product> productList;
}
