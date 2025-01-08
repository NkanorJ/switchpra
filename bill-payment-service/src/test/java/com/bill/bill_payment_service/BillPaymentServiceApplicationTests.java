package com.bill.bill_payment_service;

import com.bill.bill_payment_service.core.domain.BillPaymentStorage;
import com.bill.bill_payment_service.core.usecase.BillPaymentProductService;
import com.bill.bill_payment_service.core.usecase.PayBillUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void testGetAllProductCategory() {

        List<String> productCategories = billPaymentProductService.getAllProductCategory();

        assertNotNull(productCategories);

        assertFalse(productCategories.contains("Cable TV"));
        assertFalse(productCategories.contains("Electricity"));
        assertFalse(productCategories.contains("Internet"));
    }

}