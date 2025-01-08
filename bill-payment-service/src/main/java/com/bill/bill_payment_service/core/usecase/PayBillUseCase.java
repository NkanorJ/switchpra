package com.bill.bill_payment_service.core.usecase;

import an.awesome.pipelinr.Command;
import com.bill.bill_payment_service.commons.exception.RecordNotFoundException;
import com.bill.bill_payment_service.core.domain.BillPaymentStorage;
import com.bill.bill_payment_service.core.dto.request.BillPaymentDto;
import com.bill.bill_payment_service.core.dto.response.PayBillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayBillUseCase implements Command.Handler<BillPaymentDto, PayBillResponse> {

    private final BillPaymentProductService billPaymentProductService;

    private final BillPaymentStorage billPaymentStorage;

    @Override
    public PayBillResponse handle(BillPaymentDto billPaymentDto) {

        var bill = billPaymentProductService.findByReference(billPaymentDto.reference())
                .orElseThrow(() -> new RecordNotFoundException("bill doesn't exist", false));

        var paybill = billPaymentStorage.payBill(bill.getOrder(), bill.getName(),
                billPaymentDto.amount());

        return new PayBillResponse(paybill.amount(), paybill.customerId(), paybill.message());

    }
}
