package com.transaction.transaction_service.core.dto.request;

import an.awesome.pipelinr.Command;
import com.transaction.transaction_service.commons.enumeration.PaymentType;
import com.transaction.transaction_service.core.dto.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.Currency;

public record TransactionRequest(String accountNumber, String pin, BigDecimal amount, String expiryDate,
                                 Currency currency, PaymentType paymentType,
                                 String phoneNumber) implements Command<TransactionResponse> {

    public TransactionRequest(String accountNumber, String pin, BigDecimal amount, String expiryDate, Currency currency, PaymentType paymentType, String phoneNumber) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.amount = amount;
        this.expiryDate = expiryDate;
        this.currency = currency;
        this.paymentType = paymentType;
        this.phoneNumber = phoneNumber;
    }
}