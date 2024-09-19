package com.sparta.copang.hub.application.service;

import com.sparta.copang.hub.application.dtos.PathDto;
import com.sparta.copang.hub.domain.model.Hub;
import com.sparta.copang.hub.domain.model.Path;
import com.sparta.copang.hub.domain.repository.HubRepository;
import com.sparta.copang.hub.domain.repository.PathRepository;
import com.sparta.copang.hub.presentation.dtos.PathReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PathService {

    private final HubRepository hubRepository;
    private final PathRepository pathRepository;

    public PathDto createPath(PathReq req) {

        Hub startHub = hubRepository.findById(req.start_hub_id()).orElseThrow(() -> new RuntimeException("Hub Not Found"));
        Hub endHub = hubRepository.findById(req.end_hub_id()).orElseThrow(() -> new RuntimeException("Hub Not Found"));

        Path path = Path.createPath(req, startHub, endHub);

        startHub.getStartPaths().add(path);
        endHub.getStartPaths().add(path);

        hubRepository.save(startHub);
        hubRepository.save(endHub);

        return PathDto.of(path);
    }

}
