package com.sparta.copang.delivery.presentation.response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatusCode implements StatusCode {
    DELIVERY_NOT_FOUND(400, 400, "Delivery not found"),
    INVALID_DELIVERY_STATUS(400, 400, "Invalid delivery status");

    private final Integer httpStatusCode;
    private final Integer statusCode;
    private final String description;
}
