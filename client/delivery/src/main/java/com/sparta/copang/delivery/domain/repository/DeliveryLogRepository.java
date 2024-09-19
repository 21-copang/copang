package com.sparta.copang.delivery.domain.repository;

import com.sparta.copang.delivery.domain.entity.DeliveryLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryLogRepository {

    DeliveryLog save(DeliveryLog deliveryLog);

    List<DeliveryLog> findByDeliveryIdAndDeletedYn(UUID deliveryId, boolean b);

    Optional<DeliveryLog> findByIdAndDeletedYn(UUID deliveryLogId, boolean b);
}
