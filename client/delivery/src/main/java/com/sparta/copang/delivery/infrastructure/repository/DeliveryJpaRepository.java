package com.sparta.copang.delivery.infrastructure.repository;

import com.sparta.copang.delivery.domain.entity.Delivery;
import com.sparta.copang.delivery.domain.repository.DeliveryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, UUID>, DeliveryRepository {
}
