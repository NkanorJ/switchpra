package com.user.user_service.core.domain;

import com.user.user_service.commons.dto.SecretKey;
import com.user.user_service.commons.dto.UserDto;

import java.util.Optional;

public interface VaidateBvnStorage {

    Optional<UserDto> findByEmail(String email);

    Optional<UserDto> findByPhoneNumber(String phoneNumber);

    SecretKey createSimUser(UserDto simFintechUserDto);


}
