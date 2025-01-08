package com.bill.bill_payment_service.core.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public record PayBillResponse(BigDecimal amount, String orderId, boolean success) implements Serializable {
    public static PayBillResponse fromDto(BillPaymentResultDto result) {
        return new PayBillResponse(result.amount(), result.orderId(), result.success());
    }

    public BillPaymentResultDto toDto() {

        return new BillPaymentResultDto(amount, orderId, success);
    }
}
