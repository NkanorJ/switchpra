package com.user.user_service.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.user_service.commons.enumeration.Gender;
import com.user.user_service.commons.enumeration.Role;

import java.util.UUID;

public record UserInternalResponse(String firstName, String lastName,String password, Gender gender,
        String email, String mobile, String secretKey, @JsonIgnore UUID publicId, String dateOfBirth,
        String accountNumber, String bvn, String nin, Boolean isValidateAccount, Boolean isValidateBvn,
        Boolean isValidateNin,
        Role role) {

}
