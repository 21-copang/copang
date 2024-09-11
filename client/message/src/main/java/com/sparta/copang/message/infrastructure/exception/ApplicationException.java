package com.sparta.copang.message.infrastructure.exception;

import com.sparta.copang.message.infrastructure.response.status.StatusCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final StatusCode statusCode;

    public ApplicationException(StatusCode statusCode){
        super(statusCode.getDescription());
        this.statusCode = statusCode;
    }
}
