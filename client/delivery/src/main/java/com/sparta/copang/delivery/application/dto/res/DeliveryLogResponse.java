package com.sparta.copang.delivery.application.dto.res;

import com.sparta.copang.delivery.common.enums.DeliveryLogStatus;
import com.sparta.copang.delivery.domain.entity.DeliveryLog;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
public record DeliveryLogResponse(
        UUID id,
        Integer sequence,
        DeliveryLogStatus status,
        Float estimatedDistance,
        Float actualDistance,
        Float estimatedTime,
        Float actualTime,
        UUID startHubId,
        UUID endHubId,
        UUID managerId
) {
    public static DeliveryLogResponse of(DeliveryLog deliveryLog) {
        return DeliveryLogResponse.builder()
                .id(deliveryLog.getId())
                .sequence(deliveryLog.getSequence())
                .status(deliveryLog.getStatus())
                .estimatedDistance(deliveryLog.getEstimatedDistance())
                .actualDistance(deliveryLog.getActualDistance())
                .estimatedTime(deliveryLog.getEstimatedTime())
                .actualTime(deliveryLog.getActualTime())
                .startHubId(deliveryLog.getStartHubId())
                .endHubId(deliveryLog.getEndHubId())
                .managerId(deliveryLog.getManagerId())
                .build();
    }
}
