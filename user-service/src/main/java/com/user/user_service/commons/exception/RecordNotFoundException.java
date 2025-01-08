package com.user.user_service.commons.exception;

public class RecordNotFoundException extends SwitchPraException {
    public RecordNotFoundException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}
