package com.bill.bill_payment_service.persistencejpa.service;


import com.bill.bill_payment_service.commons.dto.UserDto;
import com.bill.bill_payment_service.gateway.UserDetailFeignClient;
import com.bill.bill_payment_service.gateway.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final UserDetailFeignClient userDetailFeignClient;

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userDetailFeignClient.findByEmail(email);
    }
}
