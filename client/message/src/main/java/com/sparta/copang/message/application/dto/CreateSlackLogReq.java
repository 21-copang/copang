package com.sparta.copang.message.application.dto;

public record CreateSlackLogReq (
        String recipientEmail,
        String message
) {}
