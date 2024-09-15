package com.sparta.copang.hub.domain.model;

import com.sparta.copang.hub.presentation.dtos.HubReq;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hub {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID hub_id;

    private String hub_name;
    private float latitude;
    private float longitude;
    private String hub_address;


    // FK (user_id)
    private UUID hub_manager;

    public static Hub createHub(HubReq req) {
        return Hub.builder()
                .hub_name(req.hub_name())
                .latitude(req.latitude())
                .longitude(req.longitude())
                .hub_address(req.hub_address())
                .hub_manager(req.manager_user_id())
                .build();
    }
}
