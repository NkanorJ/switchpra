package com.user.user_service.core.dto.request;

import an.awesome.pipelinr.Command;
import com.user.user_service.core.dto.response.GetUserResponse;

import java.util.UUID;

public record GetUserRequest(UUID publicId) implements Command<GetUserResponse> {
}
