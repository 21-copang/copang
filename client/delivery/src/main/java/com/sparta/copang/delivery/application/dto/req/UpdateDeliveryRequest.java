package com.sparta.copang.delivery.application.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateDeliveryRequest(
        @NotBlank(message = "Receiver is empty")
        String receiver,

        @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]{2,6}", message = "Invalid Email Input")
        String slackEmail,

        @NotBlank(message = "Address is empty")
        String address
) { }
