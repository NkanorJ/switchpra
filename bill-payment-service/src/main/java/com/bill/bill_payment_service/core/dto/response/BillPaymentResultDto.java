package com.bill.bill_payment_service.core.dto.response;

import java.math.BigDecimal;

public record BillPaymentResultDto(BigDecimal amount, String orderId, boolean success) {
}
