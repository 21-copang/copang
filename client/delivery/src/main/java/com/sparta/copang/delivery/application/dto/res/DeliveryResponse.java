package com.sparta.copang.delivery.application.dto.res;

import com.sparta.copang.delivery.common.enums.DeliveryStatus;
import com.sparta.copang.delivery.domain.entity.Delivery;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
public record DeliveryResponse(
        UUID id,
        DeliveryStatus status,
        String receiver,
        String slackEmail,
        String address,
        UUID orderId,
        UUID startHubId,
        UUID endHubId
) {
    public static DeliveryResponse of(Delivery delivery){

        return DeliveryResponse.builder()
                .id(delivery.getId())
                .status(delivery.getStatus())
                .receiver(delivery.getReceiver())
                .slackEmail(delivery.getSlackEmail())
                .address(delivery.getAddress())
                .orderId(delivery.getOrderId())
                .startHubId(delivery.getStartHubId())
                .endHubId(delivery.getEndHubId())
                .build();
    }
}
