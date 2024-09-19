package com.sparta.copang.order.infrastructure.client;

import com.sparta.copang.order.application.dto.delivery.CreateDeliveryRequest;
import com.sparta.copang.order.application.dto.delivery.DeliveryResponse;
import com.sparta.copang.order.infrastructure.config.FeignConfig;
import com.sparta.copang.order.presentation.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "DELIVERY-SERVICE", configuration = FeignConfig.class)
public interface DeliveryClient {

    @PostMapping("/api/deliveries")
    CommonResponse<DeliveryResponse> createDelivery(@RequestBody CreateDeliveryRequest requestDto);

    @DeleteMapping("/api/deliveries/{deliveryId}")
    CommonResponse<Object> deleteDelivery(@PathVariable UUID deliveryId);
}
