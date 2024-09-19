package com.sparta.copang.hub.presentation.dtos;

import java.util.UUID;

public record PathReq (
        float duration,
        float distance,
        UUID start_hub_id,
        UUID end_hub_id
) {
}