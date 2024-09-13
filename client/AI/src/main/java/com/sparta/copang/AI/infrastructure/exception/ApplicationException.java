package com.sparta.copang.AI.infrastructure.exception;

import com.sparta.copang.AI.infrastructure.response.status.StatusCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final StatusCode statusCode;

    public ApplicationException(StatusCode statusCode){
        super(statusCode.getDescription());
        this.statusCode = statusCode;
    }
}
