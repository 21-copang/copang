package com.sparta.copang.hub.application.dtos;

import com.sparta.copang.hub.domain.model.Path;
import lombok.Builder;

import java.time.LocalTime;
import java.util.UUID;

@Builder
public record PathDto (
        UUID path_id,
        LocalTime duration,
        float distance,
        UUID start_hub_id,
        UUID end_hub_id
) {

    public static PathDto of(Path path) {
        return PathDto.builder()
                .path_id(path.getPath_id())
                .duration(path.getDuration())
                .distance(path.getDistance())
                .start_hub_id(path.getStartHub().getHub_id())
                .end_hub_id(path.getEndHub().getHub_id())
                .build();
    }
}