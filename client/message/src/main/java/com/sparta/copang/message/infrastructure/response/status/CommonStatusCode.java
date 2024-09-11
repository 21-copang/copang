package com.sparta.copang.message.infrastructure.response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonStatusCode implements StatusCode {
    OK(200, 200, "Success"),
    BAD_REQUEST(400, 400, "Bad Request"),
    SERVER_ERROR(500, 500, "Server Error");

    private final Integer httpStatusCode;
    private final Integer statusCode;
    private final String description;
}
