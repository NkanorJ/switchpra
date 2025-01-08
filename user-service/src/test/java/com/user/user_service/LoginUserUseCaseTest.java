package com.user.user_service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.user.user_service.commons.config.JWTService;
import com.user.user_service.commons.dto.RateLimitingService;
import com.user.user_service.commons.dto.UserDto;
import com.user.user_service.commons.enumeration.Gender;
import com.user.user_service.commons.enumeration.Role;
import com.user.user_service.commons.exception.RateLimitException;
import com.user.user_service.commons.message.MessageSourceService;
import com.user.user_service.core.domain.UserStorage;
import com.user.user_service.core.dto.request.LoginUserRequest;
import com.user.user_service.core.dto.response.LoginUserResponse;
import com.user.user_service.core.usecase.LoginUserUseCase;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

class LoginUserUseCaseTest {

    @Mock
    private RateLimitingService rateLimitingService;
    @Mock
    private UserStorage userStorage;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTService jwtService;
    @Mock
    private MessageSourceService messageSourceService;
    
    @InjectMocks
    private LoginUserUseCase loginUserUseCase;

    private LoginUserRequest validRequest;
    private UserDto user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validRequest = new LoginUserRequest("janeetim2486@gmail.com", "password123");
        user = new UserDto(UUID.randomUUID(), "Jane", "Etim", Gender.F, "password123",
                          "janeetim2486@gmail.com", "07060482184", "encryptedPassword", 
                          UUID.randomUUID(), "2024-05-10", "1234567890", "12345678901", 
                          "A123B", false, false, false, Role.USER);
    }

    @Test
    void shouldReturnSuccessfulLoginResponseWhenValidCredentials() {
        Bucket bucket = mock(Bucket.class);
        when(rateLimitingService.resolveBucket(validRequest.email())).thenReturn(bucket);
        when(bucket.tryConsume(1)).thenReturn(true);
        when(userStorage.findByEmail(validRequest.email())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user.email())).thenReturn("validToken");

        LoginUserResponse response = loginUserUseCase.handle(validRequest);

        assertNotNull(response);
        assertEquals("Login successful", response.message());
        assertEquals("validToken", response.token());

        verify(rateLimitingService).resolveBucket(validRequest.email());
        verify(userStorage).findByEmail(validRequest.email());
        verify(jwtService).generateToken(user.email());
    }

    @Test
    void shouldThrowRateLimitExceptionWhenTooManyRequests() {

        Bucket bucket = mock(Bucket.class);
        when(rateLimitingService.resolveBucket(validRequest.email())).thenReturn(bucket);
        when(bucket.tryConsume(1)).thenReturn(false);

        RateLimitException exception = assertThrows(RateLimitException.class, () -> {
            loginUserUseCase.handle(validRequest);
        });

        assertEquals(null, exception.getMessage());
        verify(rateLimitingService).resolveBucket(validRequest.email());
    }

    @Test
    void shouldReturnInvalidCredentialsMessageWhenBadCredentials() {
        Bucket bucket = mock(Bucket.class);
        when(rateLimitingService.resolveBucket(validRequest.email())).thenReturn(bucket);
        when(bucket.tryConsume(1)).thenReturn(true);

        when(userStorage.findByEmail(validRequest.email())).thenReturn(Optional.of(user));

        when(jwtService.generateToken(user.email())).thenThrow(new BadCredentialsException("Bad credentials"));

        LoginUserResponse response = loginUserUseCase.handle(validRequest);

        assertNotNull(response);
        assertEquals(null, response.message());

        verify(rateLimitingService).resolveBucket(validRequest.email());
        verify(userStorage).findByEmail(validRequest.email());
        verify(jwtService).generateToken(user.email());
    }


    @Test
    void shouldReturnLoginFailedMessageWhenExceptionOccurs() {
        Bucket bucket = mock(Bucket.class);
        when(rateLimitingService.resolveBucket(validRequest.email())).thenReturn(bucket);
        when(bucket.tryConsume(1)).thenReturn(true);

        when(userStorage.findByEmail(validRequest.email())).thenReturn(Optional.of(user));

        when(jwtService.generateToken(user.email())).thenThrow(new RuntimeException("Unexpected error"));

        LoginUserResponse response = loginUserUseCase.handle(validRequest);

        assertNotNull(response);
        assertEquals(null, response.message());


        verify(rateLimitingService).resolveBucket(validRequest.email());
        verify(userStorage).findByEmail(validRequest.email());
        verify(jwtService).generateToken(user.email());
    }

}
