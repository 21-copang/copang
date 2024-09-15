package com.sparta.copang.hub.application.dtos;

import com.sparta.copang.hub.domain.model.Hub;
import lombok.Builder;

import java.util.UUID;

@Builder
public record HubDto (
        UUID hub_id,
        String hub_name,
        float latitude,
        float longitude,
        String hub_address,
        UUID hub_manager){

    public static HubDto of (Hub hub) {
        return HubDto.builder()
                .hub_id(hub.getHub_id())
                .hub_name(hub.getHub_name())
                .latitude(hub.getLatitude())
                .longitude(hub.getLongitude())
                .hub_address(hub.getHub_address())
                .hub_manager(hub.getHub_manager())
                .build();
    }
}
