package com.bill.bill_payment_service.persistencejpa.service;

import com.bill.bill_payment_service.core.domain.BillPaymentStorage;
import com.bill.bill_payment_service.core.dto.request.BillPaymentDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BillPayServiceImpl implements BillPaymentStorage {

    @Override
    public BillPaymentDto payBill(int order, String name, BigDecimal amount) {
        return new BillPaymentDto(1, "07060482184", "001",
                BigDecimal.valueOf(10), true);

    }
}
