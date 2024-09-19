package com.sparta.copang.hub.infrastructure.repository;

import com.sparta.copang.hub.domain.model.Hub;
import com.sparta.copang.hub.domain.repository.HubRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HubJpaRepository extends JpaRepository<Hub, UUID>, HubRepository {
}
