package com.user.user_service.commons.exception;

public class UserRegistrationException extends SwitchPraException {

    public UserRegistrationException(final String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}
