package com.sparta.copang.hub.application.service;

import com.sparta.copang.hub.application.dtos.WorkerDto;
import com.sparta.copang.hub.domain.model.Hub;
import com.sparta.copang.hub.domain.model.Worker;
import com.sparta.copang.hub.domain.model.WorkerRoleEnum;
import com.sparta.copang.hub.domain.repository.HubRepository;
import com.sparta.copang.hub.domain.repository.WorkerRepository;
import com.sparta.copang.hub.presentation.dtos.WorkerReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "worker")
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final HubRepository hubRepository;

    private int startHub, count;

    public List<WorkerDto> chooseWorker(UUID hubId, String role) {

        Hub hub = hubRepository.findById(hubId).orElseThrow(() -> new RuntimeException("Hub not found"));
        startHub = hub.getSequence();
        count = 1;
        return findNearWorkers(hubId, role);
    }

    public WorkerDto createWorker(WorkerReq req) {
        Hub hub = hubRepository.findById(req.hub_id()).orElseThrow(() -> new RuntimeException("Hub not found"));
        Worker worker = Worker.builder()
                .role(WorkerRoleEnum.valueOf(req.role()))
                .slackId(req.slack_id())
                .hub(hub)
                .userId(req.user_id())
                .build();

        workerRepository.save(worker);

        return WorkerDto.of(worker);
    }


    private List<WorkerDto> findNearWorkers(UUID hubId, String role) {
        Hub hub = hubRepository.findById(hubId).orElseThrow(() -> new RuntimeException("Hub not found"));
        List<Worker> workers = workerRepository.findAllByHubAndRole(hub, WorkerRoleEnum.valueOf(role));
        Long hub_count = hubRepository.countBy();

        if (!workers.isEmpty()) {
            return workers.stream().map(WorkerDto::of).collect(Collectors.toList());
        }

        while (startHub - count > 0 || startHub + count <= hub_count) {
            if (startHub - count <= 0) {
                Hub next_hub = hubRepository.findBySequence(startHub + count).orElseThrow(() -> new RuntimeException("Hub not found"));
                workers = workerRepository.findAllByHubAndRole(next_hub, WorkerRoleEnum.valueOf(role));
            } else if (startHub + count <= hub_count){
                Hub next_hub = hubRepository.findBySequence(startHub + count).orElse(hubRepository.findBySequence(startHub - count).orElseThrow(() -> new RuntimeException("Hub not found")));
                workers = workerRepository.findAllByHubAndRole(next_hub, WorkerRoleEnum.valueOf(role));
                if (!workers.isEmpty()) {
                    return workers.stream().map(WorkerDto::of).collect(Collectors.toList());
                }
                Hub previous_hub = hubRepository.findBySequence(startHub - count).orElseThrow(() -> new RuntimeException("Hub not found"));
                workers = workerRepository.findAllByHubAndRole(previous_hub, WorkerRoleEnum.valueOf(role));
            } else {
                Hub previous_hub = hubRepository.findBySequence(startHub - count).orElseThrow(() -> new RuntimeException("Hub not found"));
                workers = workerRepository.findAllByHubAndRole(previous_hub, WorkerRoleEnum.valueOf(role));
            }

            if (!workers.isEmpty()) {
                return workers.stream().map(WorkerDto::of).collect(Collectors.toList());
            }
            count++;
        }
        return workers.stream().map(WorkerDto::of).collect(Collectors.toList());
    }
}
