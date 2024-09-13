package com.sparta.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "User")
public interface AuthClient {

    // User 모듈에서 권한을 가져오는 FeignClient
    @GetMapping("/auth/getPermission/{userId}")
    String getPermission(@PathVariable Long userId);

}