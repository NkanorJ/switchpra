package com.transaction.transaction_service.factory;

import com.transaction.transaction_service.commons.enumeration.PaymentType;
import com.transaction.transaction_service.commons.exception.RecordNotFoundException;
import com.transaction.transaction_service.core.dto.response.PaymentTypeResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class USSDParser implements PaymentParser {

    @Override
    public boolean supports(String paymentType) {
        return StringUtils.isNotBlank(paymentType) && paymentType.contains(PaymentType.USSD.name());
    }

    @Override
    public PaymentTypeResponse parsePayment(String identifier) {

        return Optional.ofNullable(identifier)
                .filter(StringUtils::isNotBlank)
                .map(id -> new PaymentTypeResponse(PaymentType.USSD, identifier))
                .orElseThrow(() -> new RecordNotFoundException("Card number is blank or null", false));
    }
}