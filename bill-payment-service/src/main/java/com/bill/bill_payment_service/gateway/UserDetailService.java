package com.bill.bill_payment_service.gateway;

import com.bill.bill_payment_service.commons.dto.UserDto;

import java.util.Optional;

public interface UserDetailService {
    Optional<UserDto> findByEmail(String email);

}
