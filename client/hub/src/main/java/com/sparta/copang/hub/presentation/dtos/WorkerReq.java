package com.sparta.copang.hub.presentation.dtos;

import java.util.UUID;

public record WorkerReq(
        String role,
        String slack_id,
        UUID hub_id,
        UUID user_id
) {
}
