package com.user.user_service.persistencejpa.service;

import com.user.user_service.commons.dto.SecretKey;
import com.user.user_service.commons.dto.UserDto;
import com.user.user_service.commons.enumeration.Gender;
import com.user.user_service.commons.enumeration.Role;
import com.user.user_service.core.domain.UserStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserImpl implements UserStorage {

    private final String secretkey;

    public UserImpl(@Value("${jwt.secret.key}") String secretkey) {
        this.secretkey = secretkey;
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        if ("janeetim2486@gmail.com".equals(email)) {
            return Optional.of(new UserDto(
                    UUID.randomUUID(),
                    "Jane", "Etim", Gender.F, "password123", "janeetim2486@gmail.com",
                    "07060482184", "95c10537ccbfcaac7d0e2085e560f432ca914ed91c950f53ce4148d42a10", UUID.randomUUID(), "2024: 5: 10",
                    "1234567890", "12345678901", "A123B", false,
                    false, false, Role.USER));
        }
        return Optional.empty();

    }

    @Override
    public Optional<UserDto> findByPublicId(UUID publicId) {
        return Optional.of(new UserDto(
                UUID.randomUUID(),
                "Jane", "Etim", Gender.F, "password123", "janeetim248@gmail.com1",
                "07060482184", "secretKey", UUID.randomUUID(), "2024: 5: 10",
                "1234567890", "12345678901", "A123B", false, false,
                false, Role.USER));
    }

    @Override
    public Optional<UserDto> findByPhoneNumber(String phoneNumber) {
        if ("07060482184".equals(phoneNumber)) {
            return Optional.of(new UserDto(
                    UUID.randomUUID(),
                    "Jane", "Etim", Gender.F, "password123", "janeetim248@gmail.com",
                    "07060482184", "secretKey", UUID.randomUUID(), "2024: 5: 10",
                    "1234567890", "12345678901", "A123B", false,
                    false, false, Role.USER));
        }
        return Optional.empty();
    }

    @Override
    public SecretKey createUser(UserDto simFintechUserDto) {
        return new SecretKey("janeetim248@gmail.com", secretkey, "Jane", UUID.randomUUID());
    }

    @Override
    public void updateUser(UserDto userDto) {
        new UserDto(
                UUID.randomUUID(),
                "Jane", "Etim", Gender.F, "password123", "janeetim248@gmail.com",
                "07060482184", "secretKey", UUID.randomUUID(), "2024: 5: 1",
                "1234567890", "12345678901", "A123B", false,
                true, true, Role.USER);
    }
}
