package com.sparta.copang.delivery.infrastructure.repository;

import com.sparta.copang.delivery.domain.entity.DeliveryLog;
import com.sparta.copang.delivery.domain.repository.DeliveryLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryLogsRepository extends JpaRepository<DeliveryLog, UUID>, DeliveryLogRepository {
}
