package com.sparta.copang.hub.application.service;

import com.sparta.copang.hub.application.dtos.PathDto;
import com.sparta.copang.hub.domain.model.Hub;
import com.sparta.copang.hub.domain.model.Path;
import com.sparta.copang.hub.domain.repository.HubRepository;
import com.sparta.copang.hub.presentation.dtos.PathReq;
import com.sparta.copang.hub.presentation.dtos.PathRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PathService {

    private final HubRepository hubRepository;

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

    public PathRes searchPath(UUID startHubId, UUID endHubId) {

        if (startHubId.equals(endHubId)) {
            throw new RuntimeException("Start Hub Id and End Hub could not Equal");
        }
        Hub start = hubRepository.findById(startHubId).orElseThrow(() -> new RuntimeException("Hub Not Found"));
        Hub end = hubRepository.findById(endHubId).orElseThrow(() -> new RuntimeException("Hub Not Found"));

        boolean isForward = start.getSequence() < end.getSequence();

        List<Object[]> lists;
        if (isForward) {
            lists = hubRepository.findPathForward(startHubId, endHubId);
        } else {
            lists = hubRepository.findPathBackWard(startHubId, endHubId);
        }

        if (lists == null || lists.isEmpty()) {
            throw new RuntimeException("No Paths Found");
        }

        PathRes res = null;

        for(Object[] obj : lists) {
            float total_duration = (float) obj[0];
            float total_distance = (float) obj[1];

            res = new PathRes(startHubId, endHubId, total_duration, total_distance);
        }

        return res;
    }

}
