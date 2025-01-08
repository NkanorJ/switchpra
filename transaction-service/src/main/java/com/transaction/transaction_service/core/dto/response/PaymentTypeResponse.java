package com.transaction.transaction_service.core.dto.response;

import com.transaction.transaction_service.commons.enumeration.PaymentType;

public record PaymentTypeResponse(PaymentType paymentType, String accountIdentifier) {
}