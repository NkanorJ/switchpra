package com.user.user_service.core.domain;

import com.user.user_service.commons.dto.SecretKey;
import com.user.user_service.commons.dto.UserDto;

import java.util.Optional;
import java.util.UUID;

public interface UserStorage {

    Optional<UserDto> findByEmail(String email);

    Optional<UserDto> findByPublicId(UUID publicId);

    Optional<UserDto> findByPhoneNumber(String phoneNumber);

    SecretKey createUser(UserDto userDto);

   void updateUser (UserDto userDto);




}
