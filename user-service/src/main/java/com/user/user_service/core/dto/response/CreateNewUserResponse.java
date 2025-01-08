package com.user.user_service.core.dto.response;

import java.time.LocalDate;

public record CreateNewUserResponse(String username, String password, String email, String gender, LocalDate dateOfBirth) {
}
