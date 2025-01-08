package com.bill.bill_payment_service.commons.exception;

public class RecordNotFoundException extends SwitchPraException {
    public RecordNotFoundException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}
