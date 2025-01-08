package com.transaction.transaction_service;

import com.transaction.transaction_service.commons.enumeration.PaymentType;
import com.transaction.transaction_service.commons.exception.UnsupportedTransactionTypeException;
import com.transaction.transaction_service.core.dto.request.TransactionRequest;
import com.transaction.transaction_service.core.dto.response.PaymentTypeResponse;
import com.transaction.transaction_service.core.dto.response.TransactionResponse;
import com.transaction.transaction_service.factory.TransactionFactory;
import com.transaction.transaction_service.persistencejpa.service.TransactionImpl;
import com.transaction.transaction_service.persistencejpa.service.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceApplicationTests {
    @Mock
    private TransactionFactory transactionFactory;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionImpl transactionImpl;

    private TransactionRequest transactionRequest;

    @BeforeEach
    void setUp() {
        // Create a dummy transactionRequest for the tests
        transactionRequest = new TransactionRequest(
                "1234567890", "1234", BigDecimal.valueOf(1000), "2025-01-01", Currency.getInstance("NGN"), PaymentType.USSD, "08012345678"
        );
    }

    @Test
    void makePayment_shouldReturnSuccess_whenPaymentTypeIsUSSD() {
        var paymentTypeResponse = mock(PaymentTypeResponse.class);
        when(transactionFactory.getPaymentType(transactionRequest.paymentType().name()))
                .thenReturn(paymentTypeResponse);

        when(paymentTypeResponse.paymentType()).thenReturn(PaymentType.USSD);

        TransactionResponse transactionResponse = new TransactionResponse("Payment successful");
        when(transactionRepository.makePayment(any(TransactionRequest.class)))
                .thenReturn(transactionResponse);

        TransactionResponse response = transactionImpl.makePayment(transactionRequest);

        assertNotNull(response);
        assertEquals("Payment successful", response.status());
        verify(transactionFactory).getPaymentType(transactionRequest.paymentType().name());
        verify(transactionRepository).makePayment(any(TransactionRequest.class));
    }


    @Test
    void makePayment_shouldThrowException_whenPaymentTypeIsUnsupported() {
        var paymentTypeResponse = mock(PaymentTypeResponse.class);
        when(transactionFactory.getPaymentType(transactionRequest.paymentType().name()))
                .thenReturn(paymentTypeResponse);
        when(paymentTypeResponse.paymentType()).thenReturn(PaymentType.UNKNOWN);

        UnsupportedTransactionTypeException exception = assertThrows(UnsupportedTransactionTypeException.class,
                () -> transactionImpl.makePayment(transactionRequest));

        assertEquals("Unsupported transaction type: " + transactionRequest.paymentType(), exception.getMessage());

        verify(transactionFactory).getPaymentType(transactionRequest.paymentType().name());
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void makePayment_shouldNotCallRepository_whenPaymentTypeIsNotUSSD() {
        transactionRequest = new TransactionRequest(
                "1234567890", "1234", BigDecimal.valueOf(1000),
                "2025-01-01", Currency.getInstance("NGN"), PaymentType.MOBILE, "08012345678"
        );

        PaymentTypeResponse paymentTypeResponse = mock(PaymentTypeResponse.class);
        when(transactionFactory.getPaymentType(transactionRequest.paymentType().name()))
                .thenReturn(paymentTypeResponse);
        when(paymentTypeResponse.paymentType()).thenReturn(PaymentType.MOBILE);

        TransactionResponse response = transactionImpl.makePayment(transactionRequest);

        assertNotNull(response);
        assertEquals("Payment successful", response.status());
        verify(transactionFactory).getPaymentType(transactionRequest.paymentType().name());
        verifyNoInteractions(transactionRepository);
    }


}
