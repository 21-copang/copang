package com.sparta.copang.message.presentation.controller.response;

import com.sparta.copang.message.presentation.controller.response.status.CommonStatusCode;
import com.sparta.copang.message.presentation.controller.response.status.StatusCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonResponse<T>{
    private Result result;
    private T body;

    public static <T> CommonResponse<T> OK(T data) {
        var response = new CommonResponse<T>();
        response.result = Result.statusCode(CommonStatusCode.OK);
        response.body = data;
        return response;
    }

    public static CommonResponse<Object> ERROR(StatusCode statusCode) {
        var response = new CommonResponse<Object>();
        response.result = Result.statusCode(statusCode);
        return response;
    }

    public static CommonResponse<Object> VALID_ERROR(String message) {
        var response = new CommonResponse<Object>();
        response.result = Result.ofValidMessage(message);
        return response;
    }
}