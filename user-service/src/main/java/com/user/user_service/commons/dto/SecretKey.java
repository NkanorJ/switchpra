
package com.user.user_service.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record SecretKey(

        String email, String secretKey, String secretName, UUID publicId
) {
    @Override
    public String toString() {
        return "SecretKey{" +
                "email='" + email + '\'' +
                ", secretName='" + secretName + '\'' +
                ", publicId='" + publicId + '\'' +
                '}';
    }
}