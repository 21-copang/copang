package com.sparta.copang.order.presentation.response;

import com.sparta.copang.order.common.status.CommonStatusCode;
import com.sparta.copang.order.common.status.StatusCode;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CommonResponse<T> (Result result, T body) {

    public static <T> CommonResponse<T> OK(T data) {
        return CommonResponse.<T> builder()
                .result(Result.statusCode(CommonStatusCode.OK))
                .body(data)
                .build();
    }

    public static CommonResponse<Object> ERROR(StatusCode statusCode) {
        return CommonResponse.builder()
                .result(Result.statusCode(statusCode))
                .build();
    }

    public static CommonResponse<Object> VALID_ERROR(String message) {
        return CommonResponse.builder()
                .result(Result.ofValidMessage(message))
                .build();
    }
}