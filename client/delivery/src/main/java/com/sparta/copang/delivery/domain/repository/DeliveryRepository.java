package com.sparta.copang.delivery.domain.repository;

import com.sparta.copang.delivery.domain.entity.Delivery;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {

    Delivery save(Delivery delivery);

    Optional<Delivery> findByIdAndDeletedYn(UUID deliveryId, boolean b);
}
