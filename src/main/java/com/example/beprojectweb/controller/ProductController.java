package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.product.ProductRequest;
import com.example.beprojectweb.dto.request.product.ProductUpdateRequest;
import com.example.beprojectweb.dto.request.product.ProductUpdateStatus;
import com.example.beprojectweb.dto.response.APIResponse;
import com.example.beprojectweb.dto.response.product.ProductResponse;
import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.entity.Product;
import com.example.beprojectweb.enums.ProductStatus;
import com.example.beprojectweb.mapper.ProductMapper;
import com.example.beprojectweb.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;
    ProductMapper productMapper;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<ProductResponse> createProduct(
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("stock") int stock,
            @RequestParam("cate_ID") UUID cateId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("status") ProductStatus status
    ) {
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = productService.uploadImageToCloudinary(file);
        }

        ProductRequest request = ProductRequest.builder()
                .productName(productName)
                .description(description)
                .price(price)
                .stock(stock)
                .cate_ID(cateId)
                .urlImage(imageUrl)
                .status(status)
                .build();

        Product product = productService.createProduct(request);
        ProductResponse created = productMapper.toProductResponse(product);
        System.out.println("Received cateId: " + cateId);

        return APIResponse.<ProductResponse>builder()
                .result(created)
                .build();
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

    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<ProductResponse> updateProductWithImage(
            @PathVariable("productId") UUID productId,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("stock") int stock,
            @RequestParam("cate_ID") UUID cate_ID,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = productService.uploadImageToCloudinary(file);
        }

        ProductUpdateRequest request = ProductUpdateRequest.builder()
                .productName(productName)
                .description(description)
                .price(price)
                .stock(stock)
                .cate_ID(cate_ID)
                .urlImage(imageUrl)
                .build();

        return APIResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId, request))
                .message("Success")
                .build();
    }

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
    APIResponse<List<ProductResponse>> getNewProducts(@RequestParam(defaultValue = "6") int limit) {
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
