package com.sparta.copang.message.presentation.controller.response.status;

public interface StatusCode {
    Integer getHttpStatusCode();
    Integer getStatusCode();
    String getDescription();
}
