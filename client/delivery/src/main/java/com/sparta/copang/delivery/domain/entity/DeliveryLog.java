package com.sparta.copang.delivery.domain.entity;

import com.sparta.copang.delivery.application.dto.req.UpdateDeliveryLogRequest;
import com.sparta.copang.delivery.common.enums.DeliveryLogStatus;
import com.sparta.copang.delivery.domain.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_delivery_logs")
public class DeliveryLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_log_id")
    private UUID id;

    private Integer sequence;
    private DeliveryLogStatus status;
    private Float estimatedDistance;
    private Float actualDistance;
    private Float estimatedTime;
    private Float actualTime;
    private UUID startHubId;
    private UUID endHubId;
    private UUID managerId;
    private UUID deliveryId;

    public static DeliveryLog create(UUID deliveryId) {
        return DeliveryLog.builder()
                .sequence(1)
                .status(DeliveryLogStatus.WAIT)
                .estimatedDistance(0.0f)
                .estimatedTime(0.0f)
                .startHubId(UUID.randomUUID())
                .endHubId(UUID.randomUUID())
                .managerId(UUID.randomUUID())
                .deliveryId(deliveryId)
                .build();
    }

    public void updateStatus(DeliveryLogStatus deliveryLogStatus) {
        this.status = deliveryLogStatus;
    }

    public void updateBy(UpdateDeliveryLogRequest requestDto) {
        this.actualDistance = requestDto.actualDistance();
        this.actualTime = requestDto.actualTime();
    }
}
