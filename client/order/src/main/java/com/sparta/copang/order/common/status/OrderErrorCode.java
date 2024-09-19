package com.sparta.copang.order.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements StatusCode {
    CREATE_DELIVERY_ERROR(500, 500, "Error create delivery"),
    GET_COMPANY_ERROR(500, 500, "Get company error"),
    GET_HUB_ERROR(500, 500, "Get hub error"),
    GET_PRODUCT_ERROR(500, 500, "Get product error"),
    DELETE_DELIVERY_ERROR(500, 500, "Error delete delivery"),
    ORDER_NOT_FOUND(400, 400, "Order not found");

    private final Integer httpStatusCode;
    private final Integer statusCode;
    private final String description;
}
