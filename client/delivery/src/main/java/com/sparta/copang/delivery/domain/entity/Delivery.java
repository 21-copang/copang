package com.sparta.copang.delivery.domain.entity;

import com.sparta.copang.delivery.application.dto.req.CreateDeliveryRequest;
import com.sparta.copang.delivery.application.dto.req.UpdateDeliveryRequest;
import com.sparta.copang.delivery.common.enums.DeliveryStatus;
import com.sparta.copang.delivery.domain.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_deliveries")
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_id")
    private UUID id;

    private DeliveryStatus status;
    private String receiver;
    private String slackEmail;
    private String address;
    private UUID orderId;
    private UUID startHubId;
    private UUID endHubId;

    public static Delivery create(CreateDeliveryRequest requestDto) {
        return Delivery.builder()
                .status(DeliveryStatus.WAIT)
                .receiver(requestDto.receiver())
                .slackEmail(requestDto.slackEmail())
                .address(requestDto.address())
                .orderId(requestDto.orderId())
                .startHubId(requestDto.startHubId())
                .endHubId(requestDto.endHubId())
                .build();
    }

    public void updateBy(UpdateDeliveryRequest requestDto) {
        this.receiver = requestDto.receiver();
        this.address = requestDto.address();
        this.slackEmail = requestDto.slackEmail();
    }

    public void updateDeliveryStatus(DeliveryStatus status) {
        this.status = status;
    }
}
