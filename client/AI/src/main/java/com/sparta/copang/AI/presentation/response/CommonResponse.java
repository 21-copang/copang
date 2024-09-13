package com.sparta.copang.AI.presentation.response;

import com.sparta.copang.AI.presentation.response.status.CommonStatusCode;
import com.sparta.copang.AI.presentation.response.status.StatusCode;
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
}