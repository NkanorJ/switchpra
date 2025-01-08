package com.bill.bill_payment_service.restspring.controller;

import an.awesome.pipelinr.Pipeline;
import com.bill.bill_payment_service.core.dto.request.BillPaymentDto;
import com.bill.bill_payment_service.core.dto.response.PayBillResponse;
import com.bill.bill_payment_service.core.usecase.BillPaymentProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bill.bill_payment_service.restspring.apis.BillAPI.BILL_PAYMENT;
import static com.bill.bill_payment_service.restspring.apis.BillAPI.GET_SERVICE_CATEGORY;
import static com.bill.bill_payment_service.restspring.apis.BillAPI.PAY_BILL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BILL_PAYMENT)
public class BillPaymentController {

    private final Pipeline pipeline;
    private final BillPaymentProductService billPaymentProductService;


    @GetMapping(GET_SERVICE_CATEGORY)
    public List<String> getAllProductCategory() {
        return billPaymentProductService.getAllProductCategory();
    }

    @PostMapping(PAY_BILL)
    public PayBillResponse payBill(@RequestBody BillPaymentDto billPaymentDto) {

        return pipeline.send(billPaymentDto);
    }

}
