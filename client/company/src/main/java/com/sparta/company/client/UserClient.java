package com.sparta.company.client;

import com.sparta.company.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/auth/users/{id}")
    ApiResponse<UserResponse> getUserById(@PathVariable UUID id,
                                          @RequestHeader("X-User-Id") UUID userId,
                                          @RequestHeader("X-User-Role") String userRole);
}
