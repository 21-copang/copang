package com.sparta.product.controller;

import com.sparta.product.dto.request.ProductCreateRequest;
import com.sparta.product.dto.request.ProductUpdateRequest;
import com.sparta.product.dto.response.ProductResponse;
import com.sparta.product.service.ProductService;
import com.sparta.product.common.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 등록
    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductCreateRequest request) {
        return productService.createProduct(request);
    }

    // 상품 수정
    @PatchMapping("/{productId}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable UUID productId, @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(productId, request);
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable UUID productId) {
        return productService.getProduct(productId);
    }

    // 모든 상품 조회
    @GetMapping("/all")
    public ApiResponse<Page<ProductResponse>> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    // 상품 검색 (상품명 기준)
    @GetMapping("/search")
    public ApiResponse<Page<ProductResponse>> searchProductByName(@RequestParam(value = "productName", required = true) String productName, Pageable pageable) {
        return productService.searchProductsByName(productName, pageable);
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ApiResponse<String> deleteProduct(@PathVariable UUID productId) {
        return productService.deleteProduct(productId);
    }
}
