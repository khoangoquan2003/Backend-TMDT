package com.example.beprojectweb.controller;

import com.example.beprojectweb.dto.request.category.CategoryRequest;
import com.example.beprojectweb.dto.request.category.CategoryUpdateRequest;
import com.example.beprojectweb.dto.response.APIResponse;
import com.example.beprojectweb.dto.response.category.CategoryResponse;
import com.example.beprojectweb.dto.response.product.ProductResponse;
import com.example.beprojectweb.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping(value = "", consumes = "multipart/form-data")
    public APIResponse<CategoryResponse> createCategoryWithImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file
    ) {
        // Gọi service upload ảnh lên Cloudinary
        String imageUrl = categoryService.uploadImageToCloudinary(file);

        // Tạo request DTO
        CategoryRequest request = CategoryRequest.builder()
                .name(name)
                .description(description)
                .urlImage(imageUrl)
                .build();

        // Gọi service tạo category
        CategoryResponse created = categoryService.createCategory(request);
        return APIResponse.<CategoryResponse>builder()
                .result(created)
                .build();
    }


    @GetMapping
    public APIResponse<List<CategoryResponse>> getCategories() {
        return APIResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getCategories())
                .build();
    }

    @GetMapping("/{cate_ID}")
    public APIResponse<CategoryResponse> getCategoryById(@PathVariable UUID cate_ID){
        return APIResponse.<CategoryResponse>builder()
                .result(categoryService.getCategoryById(cate_ID))
                .build();
    }

    @PutMapping(value = "/{cate_ID}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<CategoryResponse> updateCategoryWithImage(
            @PathVariable UUID cate_ID,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = categoryService.uploadImageToCloudinary(file);
        }

        CategoryUpdateRequest request = CategoryUpdateRequest.builder()
                .name(name)
                .description(description)
                .urlImage(imageUrl)
                .build();

        CategoryResponse updatedCategory = categoryService.updateCategory(cate_ID, request);
        return APIResponse.<CategoryResponse>builder()
                .result(updatedCategory)
                .build();
    }


    @DeleteMapping("/{cate_ID}")
    public String deleteCategory(@PathVariable UUID cate_ID){
        categoryService.deleteCategory(cate_ID);
        return "Category has been deleted";
    }


}
