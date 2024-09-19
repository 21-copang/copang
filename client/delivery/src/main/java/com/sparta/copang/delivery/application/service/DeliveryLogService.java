package com.sparta.copang.delivery.application.service;

import com.sparta.copang.delivery.application.dto.req.UpdateDeliveryLogRequest;
import com.sparta.copang.delivery.application.dto.res.DeliveryLogResponse;
import com.sparta.copang.delivery.common.enums.DeliveryLogStatus;
import com.sparta.copang.delivery.common.exception.ApplicationException;
import com.sparta.copang.delivery.domain.entity.DeliveryLog;
import com.sparta.copang.delivery.domain.repository.DeliveryLogRepository;
import com.sparta.copang.delivery.presentation.response.status.DeliveryLogStatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryLogService {

    private final DeliveryLogRepository deliveryLogRepository;

    @Transactional
    public void createDeliveryLog(UUID deliveryID) {
        DeliveryLog deliveryLog = DeliveryLog.create(deliveryID);
        deliveryLogRepository.save(deliveryLog);
    }

    @Transactional(readOnly = true)
    public DeliveryLogResponse getDeliveryLog(UUID deliveryLogId) {
        DeliveryLog deliveryLog = findDeliveryLogsById(deliveryLogId);
        return DeliveryLogResponse.of(deliveryLog);
    }

    @Transactional
    public DeliveryLogResponse startDelivery(UUID deliveryLogId) {
        DeliveryLog deliveryLog = findDeliveryLogsById(deliveryLogId);

        if(deliveryLog.getStatus() != DeliveryLogStatus.WAIT)
            throw new ApplicationException(DeliveryLogStatusCode.INVALID_DELIVERY_LOG_STATUS);

        deliveryLog.updateStatus(DeliveryLogStatus.IN_DELIVERY);
        deliveryLogRepository.save(deliveryLog);

        return DeliveryLogResponse.of(deliveryLog);
    }


    @Transactional
    public Object completeDelivery(UUID deliveryLogId, UpdateDeliveryLogRequest requestDto) {
        DeliveryLog deliveryLog = findDeliveryLogsById(deliveryLogId);

        if(deliveryLog.getStatus() != DeliveryLogStatus.IN_DELIVERY)
            throw new ApplicationException(DeliveryLogStatusCode.INVALID_DELIVERY_LOG_STATUS);

        deliveryLog.updateStatus(DeliveryLogStatus.COMPLETE);
        deliveryLog.updateBy(requestDto);
        deliveryLogRepository.save(deliveryLog);

        return  DeliveryLogResponse.of(deliveryLog);
    }

    @Transactional
    public void deleteDeliveryLog(UUID deliveryId, UUID userId) {
        List<DeliveryLog> deliveryLogs = deliveryLogRepository.findByDeliveryIdAndDeletedYn(deliveryId, false);
        deliveryLogs.forEach(deliveryLog -> {deliveryLog.setDeletedYnTrue(userId);});
    }

    @Transactional
    public DeliveryLog findDeliveryLogsById(UUID deliveryLogId){
        return deliveryLogRepository.findByIdAndDeletedYn(deliveryLogId, false)
                .orElseThrow(() -> new ApplicationException(DeliveryLogStatusCode.DELIVERY_LOG_NOT_FOUND));
    }
}
