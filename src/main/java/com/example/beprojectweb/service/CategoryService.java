package com.example.beprojectweb.service;

import com.example.beprojectweb.dto.request.category.CategoryRequest;
import com.example.beprojectweb.dto.request.category.CategoryUpdateRequest;
import com.example.beprojectweb.dto.response.category.CategoryResponse;
import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.exception.AppException;
import com.example.beprojectweb.exception.ErrorCode;
import com.example.beprojectweb.mapper.CategoryMapper;
import com.example.beprojectweb.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    CloudinaryService cloudinaryService;

    /**
     * Tạo mới một danh mục
     */
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsCategoriesByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.category(request);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(saved);
    }

    /**
     * Lấy danh sách tất cả danh mục
     */
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    /**
     * Lấy thông tin chi tiết của một danh mục theo ID
     */
    public CategoryResponse getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return categoryMapper.toCategoryResponse(category);
    }

    /**
     * Cập nhật danh mục
     */
    public CategoryResponse updateCategory(UUID cate_ID, CategoryUpdateRequest request) {
        Category existing = categoryRepository.findById(cate_ID)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        categoryMapper.updateCategory(existing, request);
        Category updated = categoryRepository.save(existing);

        return categoryMapper.toCategoryResponse(updated);
    }

    /**
     * Xoá danh mục theo ID
     */
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Upload ảnh danh mục lên Cloudinary
     */
    public String uploadImageToCloudinary(MultipartFile file) {
        return cloudinaryService.uploadImage(file);
    }
}
