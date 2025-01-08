package com.bill.bill_payment_service.core.dto.request;

import an.awesome.pipelinr.Command;
import com.bill.bill_payment_service.core.dto.response.PayBillResponse;

import java.io.Serializable;
import java.math.BigDecimal;

public record BillPaymentDto(int reference, String phone, String customerId,
                             BigDecimal amount, boolean message) implements Serializable, Command<PayBillResponse> {
}
