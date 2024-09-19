package com.sparta.copang.hub.presentation.controller;

import com.sparta.copang.hub.application.dtos.PathDto;
import com.sparta.copang.hub.application.service.PathService;
import com.sparta.copang.hub.presentation.dtos.PathReq;
import com.sparta.copang.hub.presentation.dtos.PathRes;
import com.sparta.copang.hub.presentation.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/hub-path")
@RequiredArgsConstructor
public class PathController {

    private final PathService pathService;

    @PostMapping
    public ResponseEntity<CommonResponse<PathDto>> createPath(@RequestBody PathReq req) {
        return ResponseEntity.ok(CommonResponse.OK(pathService.createPath(req)));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<PathRes>> searchPath(@RequestParam UUID start,
                                                              @RequestParam UUID end) {
        return ResponseEntity.ok(CommonResponse.OK(pathService.searchPath(start, end)));
    }
}
