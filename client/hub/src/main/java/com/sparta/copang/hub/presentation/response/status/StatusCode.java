package com.sparta.copang.hub.presentation.response.status;

public interface StatusCode {
    Integer getHttpStatusCode();
    Integer getStatusCode();
    String getDescription();
}