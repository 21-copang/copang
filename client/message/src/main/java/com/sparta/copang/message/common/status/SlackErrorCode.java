package com.sparta.copang.message.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SlackErrorCode implements StatusCode {
    GET_USERID_ERROR(400, 400, "Error getting userId"),
    SEND_SLACK_MESSAGE_ERROR(400, 400, "Error sending slack message");

    private final Integer httpStatusCode;
    private final Integer statusCode;
    private final String description;

}
