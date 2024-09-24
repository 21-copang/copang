package com.sparta.copang.hub.presentation.controller;

import com.sparta.copang.hub.application.dtos.WorkerDto;
import com.sparta.copang.hub.application.service.WorkerService;
import com.sparta.copang.hub.presentation.dtos.WorkerReq;
import com.sparta.copang.hub.presentation.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/worker")
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping
    public ResponseEntity<CommonResponse<WorkerDto>> createWorker(@RequestBody WorkerReq req) {
        return ResponseEntity.ok(CommonResponse.OK(workerService.createWorker(req)));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<WorkerDto>>> chooseWorker(@RequestParam final String role,
                                                                       @RequestParam final UUID hubId) {
        return ResponseEntity.ok(CommonResponse.OK(workerService.chooseWorker(hubId, role)));
    }
}
