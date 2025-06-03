package com.example.beprojectweb.mapper;

import com.example.beprojectweb.dto.request.UserUpdateRequest;
import com.example.beprojectweb.dto.request.category.CategoryRequest;
import com.example.beprojectweb.dto.request.category.CategoryUpdateRequest;
import com.example.beprojectweb.dto.response.category.CategoryResponse;
import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryMapper {
    Category category(CategoryRequest request);
    @Mapping(source = "productList", target = "productList")  // map List<Product> -> List<ProductResponse>
    CategoryResponse toCategoryResponse(Category category);
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}
