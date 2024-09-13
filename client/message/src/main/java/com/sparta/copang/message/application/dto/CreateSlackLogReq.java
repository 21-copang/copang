package com.sparta.copang.message.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateSlackLogReq (
        @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]{2,6}", message = "Invalid Email Input")
        String recipientEmail,

        @NotBlank(message = "Message Not Blank")
        String message
) {}
