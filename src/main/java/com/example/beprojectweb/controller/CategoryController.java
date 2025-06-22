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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    public APIResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request){
        APIResponse apiResponse = new APIResponse();
        apiResponse.setResult(categoryService.createCategory(request));

        return apiResponse;
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

    @PutMapping("/{cate_ID}")
    public APIResponse<CategoryResponse> updateCategory(@PathVariable UUID cate_ID, @RequestBody CategoryUpdateRequest request) {
        // Call the service to update the category and get the response
        CategoryResponse updatedCategory = categoryService.updateCategory(cate_ID, request);

        // Return the API response with the updated category
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
