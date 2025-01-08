package com.user.user_service.commons.exception;

public class RateLimitException extends SwitchPraException {
    public RateLimitException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}
