package com.sparta.copang.delivery.presentation.response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryLogStatusCode implements StatusCode {
    DELIVERY_LOG_NOT_FOUND(400, 400, "DeliveryLog not found"),
    INVALID_DELIVERY_LOG_STATUS(400, 400, "Invalid delivery log status");

    private final Integer httpStatusCode;
    private final Integer statusCode;
    private final String description;
}
