package com.sparta.copang.hub.common.exception;

import com.sparta.copang.hub.presentation.response.status.StatusCode;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final StatusCode statusCode;

    public ApplicationException(StatusCode statusCode){
        super(statusCode.getDescription());
        this.statusCode = statusCode;
    }
}
