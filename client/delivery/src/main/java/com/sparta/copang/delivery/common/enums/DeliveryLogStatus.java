package com.sparta.copang.delivery.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DeliveryLogStatus implements CodedEnum<String> {
    WAIT("WAIT", "배송 대기"),
    IN_DELIVERY("IN_DELIVERY", "배송중"),
    COMPLETE("COMPLETE", "배송 완료");

    private final String code;
    private final String description;

    DeliveryLogStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @jakarta.persistence.Converter(autoApply = true)
    static class Converter extends AbstractCodedEnumConverter<DeliveryLogStatus, String> {
        public Converter() {
            super(DeliveryLogStatus.class);
        }
    }
}
