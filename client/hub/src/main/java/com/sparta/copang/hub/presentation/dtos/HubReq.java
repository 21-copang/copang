package com.sparta.copang.hub.presentation.dtos;

import java.util.UUID;

public record HubReq (
        String hub_name,
        float latitude,
        float longitude,
        String hub_address,
        UUID manager_user_id){
}
