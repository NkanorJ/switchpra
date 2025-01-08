package com.bill.bill_payment_service.core.dto.response;

import java.time.LocalDate;

public record GetUserResponse(String username,String email, String gender, LocalDate dateOfBirth,
                              String accountNumber) {
}
