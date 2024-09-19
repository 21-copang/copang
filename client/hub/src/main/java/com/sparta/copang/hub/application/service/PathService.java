package com.sparta.copang.hub.application.service;

import com.sparta.copang.hub.application.dtos.PathDto;
import com.sparta.copang.hub.domain.model.Path;
import com.sparta.copang.hub.domain.repository.PathRepository;
import com.sparta.copang.hub.presentation.dtos.PathReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PathService {

    private final PathRepository pathRepository;

    public PathDto createPath(PathReq req) {
        Path path = Path.createPath(req);
        pathRepository.save(path);
        return PathDto.of(path);
    }
}
