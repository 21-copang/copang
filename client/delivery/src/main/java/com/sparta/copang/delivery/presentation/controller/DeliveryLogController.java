package com.sparta.copang.delivery.presentation.controller;

import com.sparta.copang.delivery.application.dto.req.UpdateDeliveryLogRequest;
import com.sparta.copang.delivery.application.dto.res.DeliveryLogResponse;
import com.sparta.copang.delivery.application.service.DeliveryLogService;
import com.sparta.copang.delivery.presentation.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery-logs")
public class DeliveryLogController {

    private final DeliveryLogService deliveryLogService;

    @GetMapping("/{deliveryLogId}")
    public ResponseEntity<CommonResponse<DeliveryLogResponse>> getDelivery(@PathVariable(value = "deliveryLogId") UUID deliveryLogId) {
        return ResponseEntity
                .ok(CommonResponse.OK(deliveryLogService.getDeliveryLog(deliveryLogId)));
    }

    @PatchMapping("/{deliveryLogId}/start")
    @PreAuthorize("hasAnyRole('MASTER', 'HUBMANAGER', 'DELIVERY')")
    public ResponseEntity<CommonResponse<DeliveryLogResponse>> startDelivery(@PathVariable(value = "deliveryLogId") UUID deliveryLogId) {
        return ResponseEntity
                .ok(CommonResponse.OK(deliveryLogService.startDelivery(deliveryLogId)));
    }

    @PatchMapping("/{deliveryLogId}/complete")
    @PreAuthorize("hasAnyRole('MASTER', 'HUBMANAGER', 'DELIVERY')")
    public ResponseEntity<CommonResponse> completeDelivery(@PathVariable(value = "deliveryLogId") UUID deliveryLogId,
                                                           @RequestBody @Valid UpdateDeliveryLogRequest requestDto) {
        return ResponseEntity
                .ok(CommonResponse.OK(deliveryLogService.completeDelivery(deliveryLogId, requestDto)));
    }
}
