package com.sparta.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Component
public class HubClient {

    // 허브 ID를 직접 반환 (더미 데이터로 설정)
    public UUID getHubByHubId(UUID hubId) {
        // 허브 ID를 수동으로 설정
        return UUID.fromString("12345678-1234-5678-1234-567812345678"); // 더미 허브 ID
    }
}