package com.transaction.transaction_service.persistencejpa.service;


import com.transaction.transaction_service.commons.dto.UserDto;
import com.transaction.transaction_service.gateway.UserDetailFeignClient;
import com.transaction.transaction_service.gateway.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillUserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailFeignClient userDetailFeignClient;

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userDetailFeignClient.findByEmail(email);
    }
}
