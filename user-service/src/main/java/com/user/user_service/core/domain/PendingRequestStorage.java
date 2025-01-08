package com.user.user_service.core.domain;

import com.user.user_service.commons.dto.PendingDto;
import com.user.user_service.commons.dto.PendingRequestDto;
import com.user.user_service.commons.enumeration.RequestStatus;

import java.util.Optional;
import java.util.UUID;

public interface PendingRequestStorage {

    Optional<PendingDto> getPendingRequest(UUID userId);

    void createPendingRequest(PendingRequestDto request);

    void updatePendingRequest(UUID uuid, RequestStatus requestStatus);
}
