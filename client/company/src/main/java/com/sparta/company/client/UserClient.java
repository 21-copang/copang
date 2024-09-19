package com.sparta.company.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/users/{userId}")
    UUID getUserByUserId(@PathVariable UUID userId);
}
