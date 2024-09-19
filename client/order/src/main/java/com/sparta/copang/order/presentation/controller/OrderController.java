package com.sparta.copang.order.presentation.controller;

import com.sparta.copang.order.application.dto.order.CreateOrderRequest;
import com.sparta.copang.order.application.dto.order.UpdateOrderRequest;
import com.sparta.copang.order.application.dto.order.OrderResponse;
import com.sparta.copang.order.application.service.OrderService;
import com.sparta.copang.order.presentation.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<CommonResponse<OrderResponse>> createOrder(@RequestBody @Valid CreateOrderRequest requestDto) {
        return ResponseEntity
                .ok(CommonResponse.OK(orderService.createOrder(requestDto)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<OrderResponse>> getOrder(@PathVariable(name = "orderId") UUID orderId) {
        return ResponseEntity
                .ok(CommonResponse.OK(orderService.getOrder(orderId)));
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('MASTER', 'HUBMANAGER')")
    public ResponseEntity<CommonResponse<OrderResponse>> updateOrder(@PathVariable(name = "orderId") UUID orderId,
                                                                     @RequestBody @Valid UpdateOrderRequest requestDto) {
        return ResponseEntity
                .ok(CommonResponse.OK(orderService.updateOrder(orderId, requestDto)));
    }

    @PatchMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyRole('MASTER', 'HUBMANAGER')")
    public ResponseEntity<CommonResponse<OrderResponse>> cancelOrder(@PathVariable(name = "orderId") UUID orderId) {
        return ResponseEntity
                .ok(CommonResponse.OK(orderService.cancelOrder(orderId)));
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('MASTER', 'HUBMANAGER')")
    public ResponseEntity<Object> deleteOrder(@PathVariable(name = "orderId") UUID orderId,
                                              @AuthenticationPrincipal String userId) {
        orderService.deleteOrder(orderId, UUID.fromString(userId));

        return ResponseEntity
                .ok(CommonResponse.OK(null));
    }
}
