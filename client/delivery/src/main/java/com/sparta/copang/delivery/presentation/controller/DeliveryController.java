package com.sparta.copang.delivery.presentation.controller;

import com.sparta.copang.delivery.application.dto.req.CreateDeliveryRequest;
import com.sparta.copang.delivery.application.dto.req.UpdateDeliveryRequest;
import com.sparta.copang.delivery.application.dto.res.DeliveryResponse;
import com.sparta.copang.delivery.application.service.DeliveryService;
import com.sparta.copang.delivery.presentation.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<CommonResponse<DeliveryResponse>> createDelivery(@RequestBody @Valid CreateDeliveryRequest requestDto) {
        return ResponseEntity
                .ok(CommonResponse.OK(deliveryService.createDelivery(requestDto)));
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<CommonResponse<DeliveryResponse>> getDelivery(@PathVariable(value = "deliveryId") UUID deliveryId) {
        return ResponseEntity
                .ok(CommonResponse.OK(deliveryService.getDelivery(deliveryId)));
    }

    @PutMapping("/{deliveryId}")
    @PreAuthorize("hasAnyRole('MASTER', 'HUBMANAGER')")
    ResponseEntity<CommonResponse<DeliveryResponse>> updateDelivery(@PathVariable(value = "deliveryId") UUID deliveryId,
                                                                    @RequestBody @Valid UpdateDeliveryRequest requestDto) {
        return ResponseEntity
                .ok(CommonResponse.OK(deliveryService.updateDelivery(deliveryId, requestDto)));
    }

    @PatchMapping("/{deliveryId}/complete")
    @PreAuthorize("hasAnyRole('MASTER', 'HUBMANAGER', 'DELIVERY')")
    ResponseEntity<CommonResponse<DeliveryResponse>> completeDelivery(@PathVariable(value = "deliveryId") UUID deliveryId) {
        return ResponseEntity
                .ok(CommonResponse.OK(deliveryService.completeDelivery(deliveryId)));
    }

    @DeleteMapping("/{deliveryId}")
    @PreAuthorize("hasAnyRole('MASTER', 'HUBMANAGER')")
    public ResponseEntity<CommonResponse<Object>> deleteDelivery(@PathVariable(value = "deliveryId") UUID deliveryId,
                                                                 @AuthenticationPrincipal String userId) {
        deliveryService.deleteDelivery(deliveryId, UUID.fromString(userId));
        return ResponseEntity
                .ok(CommonResponse.OK(null));
    }

}
