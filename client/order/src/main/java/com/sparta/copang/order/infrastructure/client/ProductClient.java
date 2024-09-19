package com.sparta.copang.order.infrastructure.client;

import com.sparta.copang.order.application.dto.product.ProductResponse;
import com.sparta.copang.order.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "PRODUCT-SERVICE", configuration = FeignConfig.class)
public interface ProductClient {

    @GetMapping("/api/products/{productId}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable("productId") UUID productId);
}
