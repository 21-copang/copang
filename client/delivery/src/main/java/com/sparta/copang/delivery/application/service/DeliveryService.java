package com.sparta.copang.delivery.application.service;

import com.sparta.copang.delivery.application.dto.req.CreateDeliveryRequest;
import com.sparta.copang.delivery.application.dto.req.UpdateDeliveryRequest;
import com.sparta.copang.delivery.application.dto.res.DeliveryResponse;
import com.sparta.copang.delivery.common.enums.DeliveryStatus;
import com.sparta.copang.delivery.common.exception.ApplicationException;
import com.sparta.copang.delivery.domain.entity.Delivery;
import com.sparta.copang.delivery.domain.entity.DeliveryLog;
import com.sparta.copang.delivery.domain.repository.DeliveryLogRepository;
import com.sparta.copang.delivery.domain.repository.DeliveryRepository;
import com.sparta.copang.delivery.presentation.response.status.DeliveryStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryLogService deliveryLogService;

    @Transactional
    public DeliveryResponse createDelivery(CreateDeliveryRequest requestDto) {
        Delivery delivery = Delivery.create(requestDto);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        deliveryLogService.createDeliveryLog(savedDelivery.getId());

        return DeliveryResponse.of(delivery);
    }

    @Transactional(readOnly = true)
    public DeliveryResponse getDelivery(UUID deliveryId) {
        Delivery delivery = findDeliveryById(deliveryId);

        return DeliveryResponse.of(delivery);
    }

    @Transactional
    public DeliveryResponse updateDelivery(UUID deliveryId, UpdateDeliveryRequest requestDto) {
        Delivery delivery = findDeliveryById(deliveryId);
        delivery.updateBy(requestDto);
        deliveryRepository.save(delivery);

        return DeliveryResponse.of(delivery);
    }

    @Transactional
    public DeliveryResponse completeDelivery(UUID deliveryId) {
        Delivery delivery = findDeliveryById(deliveryId);

        if(delivery.getStatus() != DeliveryStatus.IN_DELIVERY)
            throw new ApplicationException(DeliveryStatusCode.INVALID_DELIVERY_STATUS);

        delivery.updateDeliveryStatus(DeliveryStatus.COMPLETE);
        deliveryRepository.save(delivery);

        return DeliveryResponse.of(delivery);
    }

    @Transactional
    public void deleteDelivery(UUID deliveryId, UUID userId) {
        Delivery delivery = findDeliveryById(deliveryId);

        if(delivery.getStatus() != DeliveryStatus.WAIT)
            throw new ApplicationException(DeliveryStatusCode.INVALID_DELIVERY_STATUS);

        delivery.setDeletedYnTrue(userId);
        deliveryRepository.save(delivery);
        deliveryLogService.deleteDeliveryLog(deliveryId, userId);
    }

    @Transactional
    public Delivery findDeliveryById(UUID deliveryId) {
        return deliveryRepository.findByIdAndDeletedYn(deliveryId, false)
                .orElseThrow(() -> new ApplicationException(DeliveryStatusCode.DELIVERY_NOT_FOUND));
    }
}
