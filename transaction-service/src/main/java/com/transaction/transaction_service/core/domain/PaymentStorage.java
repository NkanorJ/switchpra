package com.transaction.transaction_service.core.domain;

import com.transaction.transaction_service.core.dto.request.TransactionRequest;
import com.transaction.transaction_service.core.dto.response.TransactionResponse;

public interface PaymentStorage {
    TransactionResponse makePayment(TransactionRequest transactionRequest);

}