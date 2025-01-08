package com.transaction.transaction_service.commons.exception;

public class UnsupportedTransactionTypeException extends SwitchPraException {
    public UnsupportedTransactionTypeException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}
