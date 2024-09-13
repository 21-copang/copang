package com.sparta.copang.AI.infrastructure.repository;

import com.sparta.copang.AI.domain.model.AI;
import com.sparta.copang.AI.domain.repository.AIRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AIJpaRepository extends JpaRepository<AI, UUID>, AIRepository {
}
