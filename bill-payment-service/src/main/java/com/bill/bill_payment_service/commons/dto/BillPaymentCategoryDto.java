package com.bill.bill_payment_service.commons.dto;

import com.bill.bill_payment_service.commons.enumeration.ProductType;

public record BillPaymentCategoryDto(ProductType productType, boolean display, int order) {
}