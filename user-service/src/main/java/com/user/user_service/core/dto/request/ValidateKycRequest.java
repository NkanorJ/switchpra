package com.user.user_service.core.dto.request;

import an.awesome.pipelinr.Command;
import com.user.user_service.core.dto.response.ValidatekycResponse;

import java.util.UUID;

public record ValidateKycRequest(UUID publicId, String status) implements Command<ValidatekycResponse> {
}
