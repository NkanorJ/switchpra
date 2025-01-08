package com.transaction.transaction_service.restspring.controller;

import an.awesome.pipelinr.Pipeline;
import com.transaction.transaction_service.core.dto.request.TransactionRequest;
import com.transaction.transaction_service.core.dto.response.TransactionResponse;
import com.transaction.transaction_service.core.usecase.GetBanksUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.transaction.transaction_service.restspring.apis.TransactionAPI.GET_BANKS;
import static com.transaction.transaction_service.restspring.apis.TransactionAPI.MAKE_TRANSACTION;
import static com.transaction.transaction_service.restspring.apis.TransactionAPI.TRANSACTION;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRANSACTION)
public class BankController {

    private final Pipeline pipeline;
    private final GetBanksUseCase getBankService;

    @GetMapping(GET_BANKS)
    public List<String> getALlBankNames() {
        return getBankService.getBanks();

    }

    @PostMapping(MAKE_TRANSACTION)
    @ResponseStatus(value = HttpStatus.CREATED)
    public TransactionResponse makePayment(@RequestBody TransactionRequest request) {
        return pipeline.send(request);
    }

}
