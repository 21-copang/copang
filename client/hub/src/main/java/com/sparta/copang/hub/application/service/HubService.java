package com.sparta.copang.hub.application.service;

import com.sparta.copang.hub.application.dtos.HubDto;
import com.sparta.copang.hub.domain.model.Hub;
import com.sparta.copang.hub.domain.repository.HubRepository;
import com.sparta.copang.hub.presentation.dtos.HubReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;

    public HubDto createHub(HubReq req) {
        Hub hub = Hub.createHub(req);
        hubRepository.save(hub);
        return HubDto.of(hub);
    }

    public HubDto updateHub(UUID hubId, HubReq req) {
        Hub hub = hubRepository.findById(hubId).orElseThrow(() -> new RuntimeException("Hub not found"));
        hub.setHub_name(req.hub_name());
        hub.setLatitude(req.latitude());
        hub.setLongitude(req.longitude());
        hub.setHub_address(req.hub_address());
        hub.setHub_manager(req.manager_user_id());
        hubRepository.save(hub);

        return HubDto.of(hub);
    }

    public HubDto getHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId).orElseThrow(() -> new RuntimeException("Hub not found"));
        return HubDto.of(hub);
    }
}
