package com.transaction.transaction_service.factory;

import com.transaction.transaction_service.commons.enumeration.PaymentType;
import com.transaction.transaction_service.core.dto.response.PaymentTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionFactory {

    private final List<PaymentParser> parsers;

    public PaymentTypeResponse getPaymentType(String request) {

        return parsers.stream()
                .filter(p -> p.supports(request))
                .findFirst()
                .map(p -> p.parsePayment(request))
                .orElse(new PaymentTypeResponse(PaymentType.UNKNOWN, request));

    }
}