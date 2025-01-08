package com.bill.bill_payment_service.core.dto.request;

import an.awesome.pipelinr.Command;
import com.bill.bill_payment_service.commons.dto.BillPaymentCategoryDto;

import java.util.List;

public record BillPaymentCategoryRequest() implements Command<List<BillPaymentCategoryDto>> {
}
