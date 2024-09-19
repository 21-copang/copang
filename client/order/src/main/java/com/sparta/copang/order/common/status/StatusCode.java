package com.sparta.copang.order.common.status;

public interface StatusCode {
    Integer getHttpStatusCode();
    Integer getStatusCode();
    String getDescription();
}