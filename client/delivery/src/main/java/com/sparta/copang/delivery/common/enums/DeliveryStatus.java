package com.sparta.copang.delivery.common.enums;

import lombok.Getter;

@Getter
public enum DeliveryStatus implements CodedEnum<String> {
    WAIT("WAIT", "배송 대기"),
    IN_DELIVERY("IN_DELIVERY", "배송중"),
    COMPLETE("COMPLETE", "배송 완료");

    private final String code;
    private final String description;

    DeliveryStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @jakarta.persistence.Converter(autoApply = true)
     static class Converter extends AbstractCodedEnumConverter<DeliveryStatus, String> {
        public Converter() {
            super(DeliveryStatus.class);
        }
    }
}
