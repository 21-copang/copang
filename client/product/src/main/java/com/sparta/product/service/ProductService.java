package com.sparta.product.service;

import com.sparta.product.client.CompanyClient;
import com.sparta.product.client.HubClient;
import com.sparta.product.dto.request.ProductCreateRequest;
import com.sparta.product.dto.request.ProductUpdateRequest;
import com.sparta.product.dto.response.ProductResponse;
import com.sparta.product.entity.Product;
import com.sparta.product.entity.ProductRepository;
import com.sparta.product.common.ApiResponse;
import com.sparta.product.client.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyClient companyClient;
    private final HubClient hubClient;

    public ProductService(ProductRepository productRepository, CompanyClient companyClient, HubClient hubClient) {
        this.productRepository = productRepository;
        this.companyClient = companyClient;
        this.hubClient = hubClient;
    }

    // 상품 등록
    public ApiResponse<ProductResponse> createProduct(ProductCreateRequest request) {

        // 회사 정보 요청
        CompanyResponse companyResponse = companyClient.getCompanyByCompanyId(request.companyId());

        // 허브 ID를 수동으로 설정 (더미 허브 ID 사용)
        UUID hubId = hubClient.getHubByHubId(request.hubId());

        Product product = new Product(
                request.productName(),
                companyResponse.getCompanyId(),
                hubId
        );
        productRepository.save(product);

        return ApiResponse.success("OK", new ProductResponse(
                        product.getProductId(),
                        product.getProductName(),
                        companyResponse.getCompanyId(),
                        hubId),
                "상품 등록 성공");
    }

    // 상품 수정
    public ApiResponse<ProductResponse> updateProduct(UUID productId, ProductUpdateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (request.productName() != null) {
            product.setProductName(request.productName());
        }
        if (request.companyId() != null) {
            product.setCompanyId(request.companyId());
        }
        if (request.hubId() != null) {
            product.setHubId(request.hubId());
        }
        productRepository.save(product);

        // 회사 정보 요청
        CompanyResponse companyResponse = companyClient.getCompanyByCompanyId(product.getCompanyId());

        // 허브 정보 요청
        UUID hubId = hubClient.getHubByHubId(product.getHubId());

        return ApiResponse.success("OK", new ProductResponse(
                        product.getProductId(),
                        product.getProductName(),
                        companyResponse.getCompanyId(),
                        hubId),
                "상품 수정 성공");
    }

    // 상품 상세 조회
    public ApiResponse<ProductResponse> getProduct(UUID productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        // 회사 정보 요청
        CompanyResponse companyResponse = companyClient.getCompanyByCompanyId(product.getCompanyId());

        // 허브 정보 요청
        UUID hubId = hubClient.getHubByHubId(product.getHubId());

        return ApiResponse.success("OK", new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                companyResponse.getCompanyId(),
                hubId), "상품 조회 성공");
    }

    // 모든 상품 조회
    public ApiResponse<Page<ProductResponse>> getAllProducts(Pageable pageable) {

        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductResponse> responsePage = products.map(product -> {

            // 회사 정보 요청
            CompanyResponse companyResponse = companyClient.getCompanyByCompanyId(product.getCompanyId());

            // 허브 정보 요청
            UUID hubId = hubClient.getHubByHubId(product.getHubId());

            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    companyResponse.getCompanyId(),
                    hubId);
        });

        return ApiResponse.success("OK", responsePage, "모든 상품 조회 성공");
    }

    // 상품 검색
    public ApiResponse<Page<ProductResponse>> searchProductsByName(String productName, Pageable pageable) {

        Page<Product> products = productRepository.findByProductNameContaining(productName, pageable);
        Page<ProductResponse> responsePage = products.map(product -> {

            // 회사 정보 요청
            CompanyResponse companyResponse = companyClient.getCompanyByCompanyId(product.getCompanyId());

            // 허브 정보 요청
            UUID hubId = hubClient.getHubByHubId(product.getHubId());

            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    companyResponse.getCompanyId(),
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
