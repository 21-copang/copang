package com.sparta.copang.order.application.dto.delivery;

import java.util.UUID;

public record CreateDeliveryRequest(
        String receiver,
        String slackEmail,
        String address,
        UUID orderId,
        UUID startHubId,
        UUID endHubId
) { }
