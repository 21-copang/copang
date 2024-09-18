package com.sparta.copang.hub.application.service;

import com.sparta.copang.hub.application.dtos.HubDto;
import com.sparta.copang.hub.domain.model.Hub;
import com.sparta.copang.hub.domain.repository.HubRepository;
import com.sparta.copang.hub.presentation.dtos.HubReq;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Cacheable(cacheNames = "Hub", key = "args[0]")
    public HubDto getHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId).orElseThrow(() -> new RuntimeException("Hub not found"));
        return HubDto.of(hub);
    }

    public HubDto deleteHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId).orElseThrow(() -> new RuntimeException("Hub not found"));
        hub.setDeleted_at(LocalDateTime.now());

        hubRepository.save(hub);
        return HubDto.of(hub);
    }

    public List<HubDto> searchHub(String hub_name) {
        List<Hub> hubs = hubRepository.searchHub(hub_name);
        List<HubDto> hubDtos = new ArrayList<>();
        for (Hub hub : hubs) {
            hubDtos.add(HubDto.of(hub));
        }
        return hubDtos;
    }
}
