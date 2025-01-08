package com.bill.bill_payment_service.core.usecase;

import com.bill.bill_payment_service.commons.enumeration.ProductType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class BillPaymentProductService {

    public List<String> getAllProductCategory() {
        return Arrays.stream(ProductType.values())
                .map(ProductType::getName).toList();
    }


    public Optional<ProductType> findByReference(int reference) {
        return Optional.ofNullable(Arrays.stream(ProductType.values())
                .filter(productType -> productType.getOrder() == reference)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid reference: " + reference)));
    }
}