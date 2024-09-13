package com.sparta.copang.message.common.exception;

import com.sparta.copang.message.presentation.controller.response.CommonResponse;
import com.sparta.copang.message.presentation.controller.response.status.CommonStatusCode;
import com.sparta.copang.message.presentation.controller.response.status.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<CommonResponse<Object>> handleApplicationException(ApplicationException exception) {
        log.error("", exception);

        StatusCode statusCode = exception.getStatusCode();

        return ResponseEntity.status(statusCode.getHttpStatusCode())
                .body(CommonResponse.ERROR(statusCode));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Object>> handleValidException(BindingResult bindingResult) {
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();

        return ResponseEntity.status(CommonStatusCode.BAD_REQUEST.getStatusCode())
                .body(CommonResponse.VALID_ERROR(message));
    }
}