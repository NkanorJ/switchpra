package com.transaction.transaction_service.factory;

import com.transaction.transaction_service.core.dto.response.PaymentTypeResponse;

public interface PaymentParser {
    boolean supports(String paymentType);

    PaymentTypeResponse parsePayment(String accountIdentifier);
}