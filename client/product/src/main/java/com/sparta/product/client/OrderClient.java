package com.sparta.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {

    @GetMapping("/api/orders/{orderProductsId}")
    UUID getOrderByOrderProductsId(@PathVariable UUID orderProductsId);
}
