package com.example.beprojectweb.mapper;

import com.example.beprojectweb.dto.request.category.CategoryRequest;
import com.example.beprojectweb.dto.request.category.CategoryUpdateRequest;
import com.example.beprojectweb.dto.response.category.CategoryResponse;
import com.example.beprojectweb.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;


@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryMapper {

    Category category(CategoryRequest request);

    @Mapping(source = "productList", target = "productList")
    CategoryResponse toCategoryResponse(Category category);

    // Update category method with mapping for fields
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.urlImage", target = "urlImage")
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}

