package com.transaction.transaction_service.commons.exception;

public class RecordNotFoundException extends SwitchPraException {
    public RecordNotFoundException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}
