package com.bill.bill_payment_service.core.dto.response;

import com.bill.bill_payment_service.commons.dto.BillPaymentCategoryDto;
import com.bill.bill_payment_service.commons.enumeration.ProductType;

import java.util.List;

public record BillPaymentCategoryResponse(ProductType productType, boolean display,
                                          int order) {

    public static List<BillPaymentCategoryResponse> toListResponse(List<BillPaymentCategoryDto> result) {

        return result.stream()
                .map(dto -> new BillPaymentCategoryResponse(dto.productType(), dto.display(), dto.order()))
                .toList();
    }
}
