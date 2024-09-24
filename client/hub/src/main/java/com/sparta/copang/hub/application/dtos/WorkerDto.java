package com.sparta.copang.hub.application.dtos;

import com.sparta.copang.hub.domain.model.Worker;
import lombok.Builder;

import java.util.UUID;

@Builder
public record WorkerDto (
        UUID worker_id,
        String role,
        String slack_id,
        UUID hub_id,
        UUID user_id
) {

    public static WorkerDto of(Worker worker) {
        return WorkerDto.builder()
                .worker_id(worker.getWorkerId())
                .role(worker.getRole().toString())
                .slack_id(worker.getSlackId())
                .hub_id(worker.getHub().getHub_id())
                .user_id(worker.getUserId())
                .build();
    }
}
