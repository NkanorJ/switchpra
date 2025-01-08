package com.user.user_service.commons.exception;

import lombok.Getter;

@Getter
public enum ExceptionCodeEnum {
    AN_ERROR_OCCURRED("PROCESSING-ERROR-1000"),

    SUCCESSFUL("PROCESSED-SUCCESSFULLY-1001");

    private final String message;

    ExceptionCodeEnum(String message) {

        this.message = message;
    }
}
