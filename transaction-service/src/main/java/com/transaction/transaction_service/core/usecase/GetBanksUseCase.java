package com.transaction.transaction_service.core.usecase;

import com.transaction.transaction_service.commons.enumeration.BankNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetBanksUseCase {

    public List<String> getBanks() {
        return Arrays.stream(BankNames.values())
                .map(BankNames::getName).toList();
    }


}