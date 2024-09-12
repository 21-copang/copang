package com.sparta.copang.message.infrastructure.response;

import com.sparta.copang.message.infrastructure.response.status.CommonStatusCode;
import com.sparta.copang.message.infrastructure.response.status.StatusCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Result {
    private Integer resultCode;
    private String resultMessage;

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