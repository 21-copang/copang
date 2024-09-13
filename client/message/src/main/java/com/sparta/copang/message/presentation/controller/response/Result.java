package com.sparta.copang.message.presentation.controller.response;

import com.sparta.copang.message.presentation.controller.response.status.CommonStatusCode;
import com.sparta.copang.message.presentation.controller.response.status.StatusCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

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