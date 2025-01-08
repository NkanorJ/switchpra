package com.user.user_service.core.dto.response;

public record GetUserResponse(String username, String email, String gender, String dateOfBirth,
                              String accountNumber) {
}
