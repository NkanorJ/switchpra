package com.user.user_service;

import com.user.user_service.commons.dto.UserDto;
import com.user.user_service.commons.dto.UserInternalResponse;
import com.user.user_service.commons.enumeration.Gender;
import com.user.user_service.commons.enumeration.Role;
import com.user.user_service.commons.exception.RecordNotFoundException;
import com.user.user_service.commons.message.MessageSourceService;
import com.user.user_service.core.domain.UserStorage;
import com.user.user_service.core.usecase.GetUserInternalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserInternalServiceTest {

    @InjectMocks
    private GetUserInternalService getUserInternalService;

    @Mock
    private UserStorage userStorage;

    @Mock
    private MessageSourceService messageSourceService;

    @Mock
    private UserDto user;

    private final String validEmail = "jane.etim@example.com";

    @Test
    void shouldReturnUserInternalResponseWhenUserExists() {

        when(userStorage.findByEmail(validEmail)).thenReturn(Optional.of(user));

        UserInternalResponse response = getUserInternalService.handle(validEmail);

        assertNotNull(response);
        assertEquals("Jane", response.firstName());
        assertEquals("Etim", response.lastName());
        assertEquals("password123", response.password());
        assertEquals(Gender.F, response.gender());
        assertEquals("08012345678", response.mobile());
        assertEquals("secretKey", response.secretKey());
        assertNotNull(response.publicId());
        assertEquals("2000-01-01", response.dateOfBirth());
        assertEquals("1234567890", response.accountNumber());
        assertEquals("12345678901", response.bvn());
        assertEquals("A123B", response.nin());
        assertTrue(response.isValidateAccount());
        assertTrue(response.isValidateBvn());
        assertTrue(response.isValidateNin());
        assertEquals(Role.USER, response.role());

        verify(userStorage).findByEmail(validEmail);
    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenUserNotFound() {

        when(userStorage.findByEmail(validEmail)).thenReturn(Optional.empty());
        when(messageSourceService.getMessage("record.not.found")).thenReturn("User not found");

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            getUserInternalService.handle(validEmail);
        });

        assertEquals("User not found", exception.getMessage());

        verify(userStorage).findByEmail(validEmail);
    }
}
