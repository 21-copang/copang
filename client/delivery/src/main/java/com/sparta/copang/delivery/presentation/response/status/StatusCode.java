package com.sparta.copang.delivery.presentation.response.status;

public interface StatusCode {
    Integer getHttpStatusCode();
    Integer getStatusCode();
    String getDescription();
}