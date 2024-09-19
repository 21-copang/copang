package com.sparta.copang.order.infrastructure.repository;

import com.sparta.copang.order.domain.entity.Order;
import com.sparta.copang.order.domain.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID>, OrderRepository {
}
