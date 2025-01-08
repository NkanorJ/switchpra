package com.user.user_service.commons.dto;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.user.user_service.commons.enumeration.RequestStatus;
import com.user.user_service.commons.enumeration.RequestType;

import java.time.Instant;
import java.util.UUID;

public record PendingDto(RequestType requestType, RequestStatus status, UUID userId, String email,
                         String additionalInfo,
                         Instant createdAt, Instant updatedAt) implements Command<Voidy> {


}