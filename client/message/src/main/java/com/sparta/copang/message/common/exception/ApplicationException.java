package com.sparta.copang.message.common.exception;

import com.sparta.copang.message.presentation.controller.response.status.StatusCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final StatusCode statusCode;

    public ApplicationException(StatusCode statusCode){
        super(statusCode.getDescription());
        this.statusCode = statusCode;
    }
}
