package com.transaction.transaction_service.gateway;

import com.transaction.transaction_service.commons.dto.UserDto;

import java.util.Optional;

public interface UserDetailService {

    Optional<UserDto> findByEmail(String email);

}
