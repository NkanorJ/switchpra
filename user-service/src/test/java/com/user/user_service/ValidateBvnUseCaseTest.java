package com.user.user_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.user_service.commons.dto.PendingDto;
import com.user.user_service.commons.dto.PendingRequestDto;
import com.user.user_service.commons.dto.UserDto;
import com.user.user_service.commons.enumeration.Gender;
import com.user.user_service.commons.enumeration.RequestStatus;
import com.user.user_service.commons.enumeration.RequestType;
import com.user.user_service.commons.enumeration.Role;
import com.user.user_service.commons.exception.PendingDualRequestException;
import com.user.user_service.commons.exception.RecordNotFoundException;
import com.user.user_service.commons.exception.UserRoleException;
import com.user.user_service.commons.message.MessageSourceService;
import com.user.user_service.core.domain.PendingRequestStorage;
import com.user.user_service.core.domain.UserStorage;
import com.user.user_service.core.dto.request.ValidateKycRequest;
import com.user.user_service.core.dto.response.ValidatekycResponse;
import com.user.user_service.core.usecase.ValidateBvnUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ValidateBvnUseCaseTest {

    @InjectMocks
    private ValidateBvnUseCase validateBvnUseCase;

    @Mock
    private MessageSourceService messageSourceService;

    @Mock
    private UserStorage userStorage;

    @Mock
    private PendingRequestStorage pendingRequestStorage;

    @Mock
    private ObjectMapper objectMapper;

    private ValidateKycRequest validRequest;
    private UserDto user;
    private PendingRequestDto pendingRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validRequest = new ValidateKycRequest(UUID.randomUUID(), "APPROVED");

        user = new UserDto(UUID.randomUUID(),
                "Jane", "Etim", Gender.F, "password123", "janeetim2486@gmail.com",
                "07060482184", "95c10537ccbfcaac7d0e2085e560f432ca914ed91c950f53ce4148d42a10",
                UUID.randomUUID(), "2024: 5: 10",
                "1234567890", "12345678901", "A123B", false,
                false, false, Role.USER);
        pendingRequest = new PendingRequestDto(UUID.randomUUID(), UUID.randomUUID(), RequestType.KYC_VALIDATION,
                "additionalInfo", "janeetim248@gmail.com", RequestStatus.PENDING);

        when(messageSourceService.getMessage("dual.request")).thenReturn("Request already pending");
        when(messageSourceService.getMessage("record.not.found")).thenReturn("User not found");
        when(messageSourceService.getMessage("user.is.not.admin")).thenReturn("User is not admin");
        when(messageSourceService.getMessage("request.treated")).thenReturn("Request already processed");
    }

    @Test
    void shouldHandleKycRequestSuccessfullyWhenStatusIsApproved() throws JsonProcessingException {
        when(pendingRequestStorage.getPendingRequest(validRequest.publicId())).thenReturn(Optional.of(
                new PendingDto(RequestType.KYC_VALIDATION, RequestStatus.PENDING, UUID.randomUUID(),
                        "janeetim248@gmail.com", "additionalInfor", Instant.now(), Instant.now())));
        when(userStorage.findByPublicId(validRequest.publicId())).thenReturn(Optional.of(user));
        when(objectMapper.readValue(pendingRequest.additionalInfo(), UserDto.class))
                .thenReturn(new UserDto(UUID.randomUUID(), "Jane", "Etim", Gender.F, "password123",
                        "janeetim2486@gmail.com", "07060482184", "encryptedPassword",
                        UUID.randomUUID(), "2024-05-10", "1234567890", "12345678901",
                        "A123B", false, false, false, Role.USER));

        ValidatekycResponse response = validateBvnUseCase.handle(validRequest);

        assertNotNull(response);
        assertEquals("KYC request handled", response.message());

        verify(userStorage).updateUser(any(UserDto.class));
        verify(pendingRequestStorage).updatePendingRequest(eq(validRequest.publicId()), eq(RequestStatus.APPROVED));
    }

    @Test
    void shouldThrowPendingDualRequestExceptionWhenRequestAlreadyPending() {
        when(pendingRequestStorage.getPendingRequest(validRequest.publicId())).thenReturn(Optional.of(
                new PendingDto(RequestType.KYC_VALIDATION, RequestStatus.PENDING, UUID.randomUUID(),
                        "janeetim248@gmail.com", "additionalInfor", Instant.now(), Instant.now())));

        PendingDualRequestException exception = assertThrows(PendingDualRequestException.class, () -> {
            validateBvnUseCase.handle(validRequest);
        });

        assertEquals("Request already pending", exception.getMessage());
    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenUserNotFound() {
        when(pendingRequestStorage.getPendingRequest(validRequest.publicId())).thenReturn(Optional.of(
                new PendingDto(RequestType.KYC_VALIDATION, RequestStatus.PENDING, UUID.randomUUID(),
                        "janeetim248@gmail.com", "additionalInfor", Instant.now(), Instant.now())));
        when(userStorage.findByPublicId(validRequest.publicId())).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            validateBvnUseCase.handle(validRequest);
        });

        assertEquals("User not found", exception.getMessage());
    }



    @Test
    void shouldThrowPendingDualRequestExceptionWhenRequestStatusIsNotPending() {

        validRequest = new ValidateKycRequest(UUID.randomUUID(), "APPROVED");

        when(pendingRequestStorage.getPendingRequest(validRequest.publicId())).thenReturn(Optional.of(
                new PendingDto(RequestType.KYC_VALIDATION, RequestStatus.PENDING, UUID.randomUUID(),
                        "janeetim248@gmail.com", "additionalInfor", Instant.now(), Instant.now())));

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            validateBvnUseCase.handle(validRequest);
        });

        assertEquals("User not found", exception.getMessage());
    }
}
