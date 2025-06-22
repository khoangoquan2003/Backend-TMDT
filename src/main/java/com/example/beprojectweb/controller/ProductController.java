package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.product.ProductRequest;
import com.example.beprojectweb.dto.request.product.ProductUpdateStatus;
import com.example.beprojectweb.dto.response.APIResponse;
import com.example.beprojectweb.dto.response.product.ProductResponse;
import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
                .message("Successful")
                .result(productService.getProducts())
                .build();
    }


    @DeleteMapping("/{productId}")
    void deleteProduct(@PathVariable("productId") UUID productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/{productId}")
    APIResponse<ProductResponse> getProductById(@PathVariable("productId") UUID productId) {
        return APIResponse.<ProductResponse>builder()
                .result(productService.getProductById(productId))
                .message("Success")
                .build();
    }

//    @GetMapping("/{search}")
//    APIResponse<List<ProductResponse>> search(@PathVariable String search) {
//        return APIResponse.<List<ProductResponse>>builder()
//                .result(productService.getProductsByName(search))
//                .message("Success")
//                .build();
//    }

    @GetMapping("/category/{categoryId}")
    APIResponse<List<ProductResponse>> getProductsByCategory(@PathVariable("categoryId") Category category) {
        return APIResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsByCategory(category))
                .message("Success")
                .build();
    }

    @GetMapping("/total")
    APIResponse<Long> getTotalProducts() {
        return APIResponse.<Long>builder()
                .result(productService.countProducts())
                .message("Success")
                .build();
    }

    @GetMapping("/newProduct")
    APIResponse<List<ProductResponse>> getNewProducts(@RequestParam(defaultValue = "10") int limit) {
        return APIResponse.<List<ProductResponse>>builder()
                .result(productService.getNewProducts(limit))
                .message("Success")
                .build();
    }

    @PatchMapping("/{productId}/status")
    APIResponse<ProductResponse> updateProductStatus(@PathVariable UUID productId, @RequestBody ProductUpdateStatus request) {
        return APIResponse.<ProductResponse>builder()
                .result(productService.updateProductStatus(productId, request.getStatus()))
                .message("Success")
                .build();
    }


}
