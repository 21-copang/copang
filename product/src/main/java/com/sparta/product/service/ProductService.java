package com.sparta.product.service;

import com.sparta.product.client.HubClient;
import com.sparta.product.client.OrderClient;
import com.sparta.product.dto.request.ProductCreateRequest;
import com.sparta.product.dto.request.ProductUpdateRequest;
import com.sparta.product.dto.response.ProductResponse;
import com.sparta.product.entity.Product;
import com.sparta.product.entity.ProductRepository;
import com.sparta.product.common.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderClient orderClient;
    private final HubClient hubClient;

    public ProductService(ProductRepository productRepository, OrderClient orderClient, HubClient hubClient) {
        this.productRepository = productRepository;
        this.orderClient = orderClient;
        this.hubClient = hubClient;
    }

    // 상품 등록
    public ApiResponse<ProductResponse> createProduct(ProductCreateRequest request) {
        Product product = new Product(
                request.productName(),
                request.orderProductsId(),
                request.hubId()
        );
        productRepository.save(product);

        // 주문 ID 확인 (FeignClient를 통한 호출)
        UUID orderId = orderClient.getOrderByOrderProductsId(product.getOrderProductsId());

        // 허브 ID 확인 (FeignClient를 통한 호출)
        UUID hubId = hubClient.getHubByHubId(product.getHubId());

        return ApiResponse.success("OK", new ProductResponse(
                        product.getProductId(),
                        product.getProductName(),
                        orderId,  // 주문 ID
                        hubId),   // 허브 ID
                "상품 등록 성공");
    }

    // 상품 수정
    public ApiResponse<ProductResponse> updateProduct(UUID productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (request.productName() != null) {
            product.setProductName(request.productName());
        }
        if (request.orderProductsId() != null) {
            product.setOrderProductsId(request.orderProductsId());
        }
        if (request.hubId() != null) {
            product.setHubId(request.hubId());
        }
        productRepository.save(product);

        // 주문 ID 확인 (FeignClient를 통한 호출)
        UUID orderId = orderClient.getOrderByOrderProductsId(product.getOrderProductsId());

        // 허브 ID 확인 (FeignClient를 통한 호출)
        UUID hubId = hubClient.getHubByHubId(product.getHubId());

        return ApiResponse.success("OK", new ProductResponse(
                        product.getProductId(),
                        product.getProductName(),
                        orderId,  // 주문 ID
                        hubId),   // 허브 ID
                "상품 수정 성공");
    }

    // 상품 상세 조회
    public ApiResponse<ProductResponse> getProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        UUID orderId = orderClient.getOrderByOrderProductsId(product.getOrderProductsId());
        UUID hubId = hubClient.getHubByHubId(product.getHubId());

        return ApiResponse.success("OK", new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                orderId,
                hubId), "상품 조회 성공");
    }

    // 모든 상품 조회
    public ApiResponse<Page<ProductResponse>> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductResponse> responsePage = products.map(product -> {
            UUID orderId = orderClient.getOrderByOrderProductsId(product.getOrderProductsId());
            UUID hubId = hubClient.getHubByHubId(product.getHubId());
            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    orderId,
                    hubId);
        });

        return ApiResponse.success("OK", responsePage, "모든 상품 조회 성공");
    }

    // 상품 검색
    public ApiResponse<Page<ProductResponse>> searchProductsByName(String productName, Pageable pageable) {
        Page<Product> products = productRepository.findByProductNameContaining(productName, pageable);
        Page<ProductResponse> responsePage = products.map(product -> {
            UUID orderId = orderClient.getOrderByOrderProductsId(product.getOrderProductsId());
            UUID hubId = hubClient.getHubByHubId(product.getHubId());
            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    orderId,
                    hubId);
        });

        return ApiResponse.success("OK", responsePage, "상품 검색 성공");
    }

    // 상품 삭제
    public ApiResponse<String> deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        productRepository.delete(product);
        return ApiResponse.success("OK", "상품 삭제 성공", "상품 삭제 성공");
    }
}
