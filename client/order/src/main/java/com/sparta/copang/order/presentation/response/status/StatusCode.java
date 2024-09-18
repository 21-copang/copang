package com.sparta.copang.order.presentation.response.status;

public interface StatusCode {
    Integer getHttpStatusCode();
    Integer getStatusCode();
    String getDescription();
}