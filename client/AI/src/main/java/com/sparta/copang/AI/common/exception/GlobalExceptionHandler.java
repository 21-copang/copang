package com.sparta.copang.AI.common.exception;

import com.sparta.copang.AI.presentation.response.CommonResponse;
import com.sparta.copang.AI.presentation.response.status.StatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<CommonResponse<Object>> handleApplicationException(ApplicationException exception) {

        StatusCode statusCode = exception.getStatusCode();

        return ResponseEntity.status(statusCode.getHttpStatusCode())
                .body(CommonResponse.ERROR(statusCode));
    }
}