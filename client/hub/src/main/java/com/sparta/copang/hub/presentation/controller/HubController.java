package com.sparta.copang.hub.presentation.controller;

import com.sparta.copang.hub.application.dtos.HubDto;
import com.sparta.copang.hub.application.service.HubService;
import com.sparta.copang.hub.presentation.dtos.HubReq;
import com.sparta.copang.hub.presentation.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/hub")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping
    public ResponseEntity<CommonResponse<HubDto>> createHub(@RequestBody HubReq req) {
        return ResponseEntity.ok(CommonResponse.OK(hubService.createHub(req)));
    }

    @PutMapping("/{hubId}")
    public ResponseEntity<CommonResponse<HubDto>> updateHub(@PathVariable UUID hubId, @RequestBody HubReq req) {
        return ResponseEntity.ok(CommonResponse.OK(hubService.updateHub(hubId, req)));
    }
}
