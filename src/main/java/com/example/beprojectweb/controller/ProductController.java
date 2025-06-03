package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.product.ProductRequest;
import com.example.beprojectweb.dto.response.APIResponse;
import com.example.beprojectweb.dto.response.product.ProductResponse;
import com.example.beprojectweb.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @PostMapping
    public APIResponse<ProductResponse> createProduct(@RequestBody ProductRequest request){
        APIResponse apiResponse = new APIResponse<>();
        apiResponse.setResult(productService.createProduct(request));

        return apiResponse;
    }

    @GetMapping
    public APIResponse<List<ProductResponse>> getProducts(){
        return APIResponse.<List<ProductResponse>>builder()
                .result(productService.getProducts())
                .build();
    }

}
