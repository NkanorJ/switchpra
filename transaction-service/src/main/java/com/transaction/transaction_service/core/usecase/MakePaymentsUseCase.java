package com.transaction.transaction_service.core.usecase;

import an.awesome.pipelinr.Command;
import com.transaction.transaction_service.core.domain.PaymentStorage;
import com.transaction.transaction_service.core.dto.request.TransactionRequest;
import com.transaction.transaction_service.core.dto.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MakePaymentsUseCase implements Command.Handler<TransactionRequest, TransactionResponse> {

    private final PaymentStorage paymentStorage;

    @Override
    public TransactionResponse handle(TransactionRequest request) {

        return paymentStorage.makePayment(request);
    }
}