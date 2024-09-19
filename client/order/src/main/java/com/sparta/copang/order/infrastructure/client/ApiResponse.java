package com.sparta.copang.order.infrastructure.client;

public record ApiResponse<T> (
    String status,
    Boolean success,
    T data,
    String message
) { }