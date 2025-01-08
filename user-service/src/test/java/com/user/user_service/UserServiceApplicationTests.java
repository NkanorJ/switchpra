package com.user.user_service;

import com.user.user_service.commons.dto.PendingDto;
import com.user.user_service.commons.dto.PendingRequestDto;
import com.user.user_service.commons.dto.RateLimitingService;
import com.user.user_service.commons.dto.SecretKey;
import com.user.user_service.commons.dto.UserDto;
import com.user.user_service.commons.enumeration.Gender;
import com.user.user_service.commons.enumeration.RequestStatus;
import com.user.user_service.commons.enumeration.RequestType;
import com.user.user_service.commons.enumeration.Role;
import com.user.user_service.commons.exception.PendingDualRequestException;
import com.user.user_service.commons.exception.PhoneNumberException;
import com.user.user_service.commons.exception.RateLimitException;
import com.user.user_service.commons.exception.UserRegistrationException;
import com.user.user_service.commons.message.MessageSourceService;
import com.user.user_service.core.domain.PendingRequestStorage;
import com.user.user_service.core.domain.UserStorage;
import com.user.user_service.core.dto.request.CreateNewUserRequest;
import com.user.user_service.core.usecase.CreateNewUserUseCase;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Mock
    private UserStorage userStorage;

    @Mock
    private RateLimitingService rateLimitingService;

    @Mock
    private MessageSourceService messageSourceService;

    @Mock
    private PendingRequestStorage pendingRequestStorage;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private CreateNewUserUseCase createNewUserUseCase;

    private CreateNewUserRequest validRequest;

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        validRequest = new CreateNewUserRequest("John", "Etim", "john.etim@example.com", "password123",
                "07012345678", "MALE", "1990-01-01", "123456789012",
                "123456789", UUID.randomUUID().toString());

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(userStorage.findByPhoneNumber(validRequest.phoneNumber())).thenReturn(Optional.empty());
        when(userStorage.findByEmail(validRequest.email())).thenReturn(Optional.empty());
        when(rateLimitingService.resolveIPAddress(anyString())).thenReturn(mock(Bucket.class));
        when(rateLimitingService.resolveIPAddress(anyString()).tryConsume(1)).thenReturn(true);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        when(pendingRequestStorage.getPendingRequest(any(UUID.class))).thenReturn(Optional.empty());

        SecretKey secretKey = createNewUserUseCase.handle(validRequest);

        assertNotNull(secretKey);
        assertEquals(validRequest.email(), secretKey.email());
        assertNotNull(secretKey.publicId());

        verify(userStorage).findByPhoneNumber(validRequest.phoneNumber());
        verify(userStorage).findByEmail(validRequest.email());
        verify(rateLimitingService).resolveIPAddress(anyString());
        verify(bCryptPasswordEncoder).encode(validRequest.password());
        verify(pendingRequestStorage).createPendingRequest(any(PendingRequestDto.class));
    }

    @Test
    void shouldThrowPhoneNumberExceptionWhenUserExists() {

        when(userStorage.findByPhoneNumber(validRequest.phoneNumber())).thenReturn(Optional.of(new UserDto(UUID.randomUUID(),
                "Jane", "Etim", Gender.F, "password123", "janeetim2486@gmail.com",
                "07060482184", "95c10537ccbfcaac7d0e2085e560f432ca914ed91c950f53ce4148d42a10",
                UUID.randomUUID(), "2024: 5: 10",
                "1234567890", "12345678901", "A123B", false,
                false, false, Role.USER)));

        PhoneNumberException exception = assertThrows(PhoneNumberException.class, () -> {
            createNewUserUseCase.handle(validRequest);
        });

        assertEquals(String.format("Invalid phone number format [%s]", validRequest.phoneNumber()), exception.getMessage());
        verify(userStorage);
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        when(userStorage.findByEmail(validRequest.email())).thenReturn(Optional.of(new UserDto(UUID.randomUUID(),
                "Jane", "Etim", Gender.F, "password123", "janeetim2486@gmail.com",
                "07060482184", "95c10537ccbfcaac7d0e2085e560f432ca914ed91c950f53ce4148d42a10",
                UUID.randomUUID(), "2024: 5: 10", "1234567890", "12345678901", "A123B", false,
                false, false, Role.USER)));

        PhoneNumberException exception = assertThrows(PhoneNumberException.class, () -> {
            createNewUserUseCase.handle(validRequest);
        });

        assertEquals(String.format("Invalid phone number format [%s]", validRequest.phoneNumber()), exception.getMessage());
        verifyNoInteractions(userStorage);
    }

    @Test
    void shouldThrowPendingDualRequestExceptionWhenRequestAlreadyPending() {
        PendingRequestDto pendingRequest = new PendingRequestDto(
                UUID.randomUUID(), UUID.randomUUID(), RequestType.KYC_VALIDATION,
                "additionalInfo", "janeetim248@gmail.com", RequestStatus.PENDING
        );

        var PendingDto = new PendingDto(RequestType.KYC_VALIDATION, RequestStatus.PENDING, UUID.randomUUID(),
                "email", "additional", Instant.now(), Instant.now());

        when(pendingRequestStorage.getPendingRequest(any(UUID.class))).thenReturn(Optional.of(PendingDto));

        PendingDualRequestException exception = assertThrows(PendingDualRequestException.class, () -> {
            createNewUserUseCase.handle(validRequest);
        });

        assertEquals("dual.request", exception.getMessage());

        verify(pendingRequestStorage).getPendingRequest(any(UUID.class));
    }

}
