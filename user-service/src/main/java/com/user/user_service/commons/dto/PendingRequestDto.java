package com.user.user_service.commons.dto;

import com.user.user_service.commons.enumeration.RequestStatus;
import com.user.user_service.commons.enumeration.RequestType;

import java.util.UUID;

public record PendingRequestDto(UUID userId, UUID requestId, RequestType requestType, String additionalInfo,
                                String email, RequestStatus status) {

}