package com.example.beprojectweb.service;

import com.example.beprojectweb.dto.request.product.ProductRequest;
import com.example.beprojectweb.dto.request.product.ProductUpdateRequest;
import com.example.beprojectweb.dto.response.product.ProductResponse;
import com.example.beprojectweb.entity.Category;
import com.example.beprojectweb.entity.Product;
import com.example.beprojectweb.mapper.ProductMapper;
import com.example.beprojectweb.repository.CategoryRepository;
import com.example.beprojectweb.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

            return productRepository.save(existingProduct);
        }

        // Nếu là sản phẩm mới thì phải tìm Category và gán vào trước khi lưu
        Category category = categoryRepository.findById(request.getCate_ID())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product newProduct = productMapper.toProduct(request);
        newProduct.setCategory(category);

        return productRepository.save(newProduct);
    }

    public ProductResponse updateProduct(UUID id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateProduct(product, request);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getProducts(){
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return  productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByName(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword)
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public Long countProducts() {
        return productRepository.count();
    }

    public List<ProductResponse> getNewProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Product> products = productRepository.findAllByOrderByCreatedAtDesc(pageable);
        return products.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public Product fetchProductById(UUID id) throws Exception {
        return productRepository.findById(id).orElseThrow(BadRequestException::new);
    }


}
