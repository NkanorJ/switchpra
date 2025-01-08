package com.user.user_service.commons.exception;

public abstract class SwitchPraException extends RuntimeException {

    protected final ExceptionCodeEnum code;

    protected final boolean printStackTrace;

    protected SwitchPraException(String message, boolean printStackTrace) {
        super(message);
        this.code = ExceptionCodeEnum.AN_ERROR_OCCURRED;
        this.printStackTrace = printStackTrace;
    }
}
