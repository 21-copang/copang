package com.sparta.copang.order.domain.repository;

import com.sparta.copang.order.domain.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByIdAndDeletedYn(UUID orderId, boolean b);

}
