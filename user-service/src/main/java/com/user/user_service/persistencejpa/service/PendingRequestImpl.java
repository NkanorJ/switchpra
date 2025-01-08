package com.user.user_service.persistencejpa.service;

import com.user.user_service.commons.dto.PendingDto;
import com.user.user_service.commons.dto.PendingRequestDto;
import com.user.user_service.commons.enumeration.RequestStatus;
import com.user.user_service.commons.enumeration.RequestType;
import com.user.user_service.core.domain.PendingRequestStorage;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class PendingRequestImpl implements PendingRequestStorage {

    @Override
    public Optional<PendingDto> getPendingRequest(UUID userId) {
        return Optional.of(new PendingDto(RequestType.KYC_VALIDATION, RequestStatus.PENDING, UUID.randomUUID(),
                "janeetim248@gmail.com", "additionalInfor", Instant.now(), Instant.now()));
    }

    @Override
    public void createPendingRequest(PendingRequestDto request) {

    }

    @Override
    public void updatePendingRequest(UUID uuid, RequestStatus requestStatus) {
        new PendingDto(RequestType.KYC_VALIDATION, RequestStatus.APPROVED, UUID.randomUUID(),
                "janeetim248@gmail.com", "additionalInfor", Instant.now(), Instant.now());
    }
}
