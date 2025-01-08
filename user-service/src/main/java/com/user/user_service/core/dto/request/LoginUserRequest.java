package com.user.user_service.core.dto.request;

import an.awesome.pipelinr.Command;
import com.user.user_service.core.dto.response.LoginUserResponse;

public record LoginUserRequest(String email, String password) implements Command<LoginUserResponse> {

}
