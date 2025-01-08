package com.transaction.transaction_service.persistencejpa.service;

import com.transaction.transaction_service.commons.enumeration.PaymentType;
import com.transaction.transaction_service.commons.exception.UnsupportedTransactionTypeException;
import com.transaction.transaction_service.core.domain.PaymentStorage;
import com.transaction.transaction_service.core.dto.request.TransactionRequest;
import com.transaction.transaction_service.core.dto.response.TransactionResponse;
import com.transaction.transaction_service.factory.TransactionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionImpl implements PaymentStorage {

    private final TransactionFactory transactionFactory;

    private final TransactionRepository transactionRepository;


    @Override
    public TransactionResponse makePayment(TransactionRequest transactionRequest) {
        var paymentChannelResponse = transactionFactory.getPaymentType(transactionRequest.paymentType().name());

        if (paymentChannelResponse.paymentType() == null) {
            throw new UnsupportedTransactionTypeException("Unsupported transaction type: " + transactionRequest.paymentType(), false);
        }

        if (paymentChannelResponse.paymentType().equals(PaymentType.USSD)) {
            var transaction = new TransactionRequest(transactionRequest.accountNumber(), transactionRequest.pin(), transactionRequest.amount(),
                    transactionRequest.expiryDate(), transactionRequest.currency(), transactionRequest.paymentType(), transactionRequest.phoneNumber());

            transactionRepository.makePayment(transaction);

        }

        return new TransactionResponse("Payment successful");
    }
}