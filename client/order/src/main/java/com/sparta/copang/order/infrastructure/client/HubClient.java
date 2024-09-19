package com.sparta.copang.order.infrastructure.client;

import com.sparta.copang.order.application.dto.hub.HubResponse;
import com.sparta.copang.order.infrastructure.config.FeignConfig;
import com.sparta.copang.order.presentation.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "HUB-SERVICE", configuration = FeignConfig.class)
public interface HubClient {

    @GetMapping("/api/hubs/{hubId}")
    CommonResponse<HubResponse> getHubId(@PathVariable("hubId") UUID hubId);
}
