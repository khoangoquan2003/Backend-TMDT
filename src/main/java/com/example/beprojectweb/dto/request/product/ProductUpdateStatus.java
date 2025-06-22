package com.example.beprojectweb.dto.request.product;

import com.example.beprojectweb.enums.ProductStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateStatus {
    ProductStatus status;
}
