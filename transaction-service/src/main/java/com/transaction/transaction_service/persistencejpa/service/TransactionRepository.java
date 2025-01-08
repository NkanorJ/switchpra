package com.transaction.transaction_service.persistencejpa.service;

import com.transaction.transaction_service.core.dto.request.TransactionRequest;
import com.transaction.transaction_service.core.dto.response.TransactionResponse;
import org.springframework.stereotype.Service;

@Service
public class TransactionRepository {

    public TransactionResponse makePayment(TransactionRequest request) {

        return new TransactionResponse("Payment successful");
    }
}