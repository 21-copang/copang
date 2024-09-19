package com.sparta.copang.hub.infrastructure.repository;

import com.sparta.copang.hub.domain.model.Path;
import com.sparta.copang.hub.domain.repository.PathRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PathJpaRepository extends JpaRepository<Path, UUID>, PathRepository {
}