package com.sparta.copang.hub.infrastructure.repository;

import com.sparta.copang.hub.domain.model.Worker;
import com.sparta.copang.hub.domain.repository.WorkerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkerJpaRepository extends JpaRepository<Worker, UUID>, WorkerRepository {
}
