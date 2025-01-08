package com.bill.bill_payment_service.core.domain;

import com.bill.bill_payment_service.core.dto.request.BillPaymentDto;

import java.math.BigDecimal;

public interface BillPaymentStorage {

    BillPaymentDto payBill(int order, String name, BigDecimal amount);
}
