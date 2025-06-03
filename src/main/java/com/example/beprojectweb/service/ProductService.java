package com.example.beprojectweb.service;

import com.example.beprojectweb.dto.request.product.ProductRequest;
import com.example.beprojectweb.dto.response.product.ProductResponse;
import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.entity.Product;
import com.example.beprojectweb.mapper.ProductMapper;
import com.example.beprojectweb.repository.CategoryRepository;
import com.example.beprojectweb.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;

    public Product createProduct(ProductRequest request) {
        // Kiểm tra xem sản phẩm đã tồn tại theo tên chưa
        Optional<Product> optionalProduct = productRepository.findByProductName(request.getProductName());

        if (optionalProduct.isPresent()) {
            // Nếu sản phẩm đã tồn tại, cập nhật số lượng tồn kho
            Product existingProduct = optionalProduct.get();
            existingProduct.setStock(existingProduct.getStock() + request.getStock());

            // Có thể cập nhật thêm thông tin khác nếu muốn
            productMapper.updateProduct(existingProduct, request);

            return productRepository.save(existingProduct);
        }

        // Nếu là sản phẩm mới thì phải tìm Category và gán vào trước khi lưu
        Category category = categoryRepository.findById(request.getCate_ID())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product newProduct = productMapper.toProduct(request);
        newProduct.setCategory(category);

        return productRepository.save(newProduct);
    }

    public List<ProductResponse> getProducts(){
        return productRepository.findAll()
                .stream()
                .map(product -> productMapper.toProductResponse(product))
                .toList();
    }
}
