package com.bill.bill_payment_service;

import com.bill.bill_payment_service.commons.enumeration.ProductType;
import com.bill.bill_payment_service.commons.exception.RecordNotFoundException;
import com.bill.bill_payment_service.core.domain.BillPaymentStorage;
import com.bill.bill_payment_service.core.dto.request.BillPaymentDto;
import com.bill.bill_payment_service.core.dto.response.PayBillResponse;
import com.bill.bill_payment_service.core.usecase.BillPaymentProductService;
import com.bill.bill_payment_service.core.usecase.PayBillUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PayBillUseCase.class)
class BillPaymentServiceApplicationTests {

    @MockBean
    BillPaymentProductService billPaymentProductService;

    @MockBean
    BillPaymentStorage billPaymentStorage;

    @InjectMocks
    private PayBillUseCase billUseCase;

    @BeforeEach
    void setup() {
        this.billUseCase = new PayBillUseCase(billPaymentProductService, billPaymentStorage);
    }


    @Test
    void handle_shouldReturnPayBillResponse_whenBillExists() {

        var billPaymentDto = new BillPaymentDto(1, "07060482184", "1", BigDecimal.valueOf(50), true);
        var billResponse = new BillPaymentDto(1, "07060482184", "Electricity", BigDecimal.valueOf(50), true);
        var payBillResponse = new PayBillResponse(BigDecimal.valueOf(50), "customer123", true);

        when(billPaymentProductService.findByReference(billPaymentDto.reference()))
                .thenReturn(Optional.of(ProductType.ELECTRICITY));
        when(billPaymentStorage.payBill(1, "Electricity", billPaymentDto.amount()))
                .thenReturn(billResponse);

        PayBillResponse response = billUseCase.handle(billPaymentDto);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(50), response.amount());
        assertEquals("Electricity", response.orderId());
        assertTrue(response.success());

        verify(billPaymentProductService).findByReference(billPaymentDto.reference());
        verify(billPaymentStorage).payBill(1, "Electricity", billPaymentDto.amount());
    }


    @Test
    void handle_shouldThrowException_whenBillDoesNotExist() {

        var billPaymentDto = new BillPaymentDto(1, "07060482184", "1", BigDecimal.valueOf(50), true);

        when(billPaymentProductService.findByReference(billPaymentDto.reference())).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class,
                () -> billUseCase.handle(billPaymentDto));

        assertEquals("bill doesn't exist", exception.getMessage());
        verify(billPaymentProductService).findByReference(billPaymentDto.reference());
        verifyNoInteractions(billPaymentStorage);
    }

    @Test
    void testGetAllProductCategory_shouldReturnValidCategories() {
        when(billPaymentProductService.getAllProductCategory())
                .thenReturn(List.of("Cable TV", "Electricity", "Internet", "Education"));

        var productCategories = billPaymentProductService.getAllProductCategory();

        assertNotNull(productCategories);
        assertTrue(productCategories.contains("Cable TV"));
        assertTrue(productCategories.contains("Electricity"));
        assertTrue(productCategories.contains("Internet"));
        assertTrue(productCategories.contains("Education"));

        verify(billPaymentProductService).getAllProductCategory();
    }

}