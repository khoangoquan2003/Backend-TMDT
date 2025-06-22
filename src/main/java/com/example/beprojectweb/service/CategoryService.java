package com.example.beprojectweb.service;

import com.example.beprojectweb.dto.request.category.CategoryRequest;
import com.example.beprojectweb.dto.request.category.CategoryUpdateRequest;
import com.example.beprojectweb.dto.response.APIResponse;
import com.example.beprojectweb.dto.response.UserResponse;
import com.example.beprojectweb.dto.response.category.CategoryResponse;
import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.exception.AppException;
import com.example.beprojectweb.exception.ErrorCode;
import com.example.beprojectweb.mapper.CategoryMapper;
import com.example.beprojectweb.mapper.UserMapper;
import com.example.beprojectweb.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public Category createCategory(CategoryRequest request){
        if(categoryRepository.existsCategoriesByName(request.getName()))
            throw new AppException(ErrorCode.CATEGORY_EXISTED);

        Category category = categoryMapper.category(request);
        return categoryRepository.save(category);
    }

    public List<CategoryResponse> getCategories(){

        return categoryRepository.findAll()
                .stream()
                .map(category -> categoryMapper.toCategoryResponse(category))
                .toList();
    }

    public CategoryResponse getCategoryById(UUID id){
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found")));
    }

    public CategoryResponse updateCategory(UUID cate_ID, CategoryUpdateRequest request) {
        // Fetch the existing Category by cate_ID
        Category existingCategory = categoryRepository.findById(cate_ID)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Update the existing Category using the mapper
        categoryMapper.updateCategory(existingCategory, request);

        // Save the updated category back to the repository
        Category updatedCategory = categoryRepository.save(existingCategory);

        // Convert the updated Category entity to a CategoryResponse
        return categoryMapper.toCategoryResponse(updatedCategory);
    }

    public void deleteCategory(UUID id){
        categoryRepository.deleteById(id);
    }

}
