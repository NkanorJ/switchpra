package com.user.user_service.commons.exception;

public class PendingDualRequestException extends SwitchPraException {
    public PendingDualRequestException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}
