package com.sparta.copang.order.presentation.response;

import com.sparta.copang.order.presentation.response.status.CommonStatusCode;
import com.sparta.copang.order.presentation.response.status.StatusCode;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record Result (Integer resultCode, String resultMessage) {

    public static Result statusCode(StatusCode statusCode){
        return Result.builder()
                .resultCode(statusCode.getStatusCode())
                .resultMessage(statusCode.getDescription())
                .build();
    }

    public static Result ofValidMessage(String message){
        return Result.builder()
                .resultCode(CommonStatusCode.BAD_REQUEST.getStatusCode())
                .resultMessage(message)
                .build();
    }
}