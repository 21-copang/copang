package com.sparta.copang.AI.infrastructure.response.status;

public interface StatusCode {
    Integer getHttpStatusCode();
    Integer getStatusCode();
    String getDescription();
}
