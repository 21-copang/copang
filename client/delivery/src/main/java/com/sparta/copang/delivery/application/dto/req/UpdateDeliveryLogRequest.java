package com.sparta.copang.delivery.application.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateDeliveryLogRequest(
        @NotNull(message = "ActualDistance is null")
        @Positive( message = "ActualDistance is less than 0")
        Float actualDistance,

        @NotNull(message = "ActualTime is null")
        @Positive(message = "ActualTime is less than 0")
        Float actualTime
) { }
