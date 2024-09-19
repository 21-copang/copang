package com.sparta.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "HUB-SERVICE")
public interface HubClient {

    @GetMapping("/api/hubs/{hubId}")
    UUID getHubByHubId(@PathVariable UUID hubId);
}
