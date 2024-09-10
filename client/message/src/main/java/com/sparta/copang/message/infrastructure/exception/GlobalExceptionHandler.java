package com.sparta.copang.message.infrastructure.exception;

import com.sparta.copang.message.infrastructure.response.CommonResponse;
import com.sparta.copang.message.infrastructure.response.status.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<CommonResponse<Object>> apiException(ApplicationException exception) {
        log.error("", exception);

        StatusCode statusCode = exception.getStatusCode();

        return ResponseEntity.status(statusCode.getHttpStatusCode())
                .body(CommonResponse.ERROR(statusCode));
    }

}