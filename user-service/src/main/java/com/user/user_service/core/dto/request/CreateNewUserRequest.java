package com.user.user_service.core.dto.request;

import an.awesome.pipelinr.Command;
import com.user.user_service.commons.dto.SecretKey;
import com.user.user_service.core.dto.response.CreateNewUserResponse;

import java.time.LocalDate;

public record CreateNewUserRequest(String username, String phoneNumber, String firstName, String lastName, String password,
                                   String bvn, String nin, String email, String gender, String dateOfBirth) implements Command<SecretKey> {
}
