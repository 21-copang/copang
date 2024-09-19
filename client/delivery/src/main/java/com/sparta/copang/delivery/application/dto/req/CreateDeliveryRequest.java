package com.sparta.copang.delivery.application.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CreateDeliveryRequest(
        @NotBlank(message = "Receiver is empty")
        String receiver,

        @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]{2,6}", message = "Invalid Email Input")
        String slackEmail,

        @NotBlank(message = "Address is empty")
        String address,

        @NotNull(message = "OrderId is null")
        UUID orderId,

        @NotNull(message = "StartHubId is null")
        UUID startHubId,

        @NotNull(message = "EndHubId is null")
        UUID endHubId
) {}
