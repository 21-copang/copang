package com.sparta.copang.hub.domain.repository;

import com.sparta.copang.hub.domain.model.Hub;
import com.sparta.copang.hub.domain.model.Worker;
import com.sparta.copang.hub.domain.model.WorkerRoleEnum;

import java.util.List;

public interface WorkerRepository {
    List<Worker> findAllByHubAndRole(Hub hub, WorkerRoleEnum role);
    Worker save(Worker worker);
}
