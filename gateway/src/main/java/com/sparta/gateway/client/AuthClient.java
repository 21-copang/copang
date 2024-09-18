package com.sparta.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "USER-SERVICE")
public interface AuthClient {

    // User 모듈에서 권한을 가져오는 FeignClient
    @GetMapping("/auth/getPermission/{userId}")
    String getPermission(@PathVariable UUID userId);

}