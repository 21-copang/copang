package com.sparta.copang.hub.presentation.dtos;

import java.util.UUID;

public record PathRes (
        UUID startHubId,
        UUID endHubId,
        float total_duration,
        float total_distance
) {
}
