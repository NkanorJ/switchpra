package com.user.user_service.restspring.apis;

public interface UserAPI {
    String USER = "/api/v1/user";

    String CREATE_NEW_USER = "/create";

    String VALIDATE_BVN = "/validate_kyc";

    String GET_USER = "/{publicId}";

    String LOGIN = "/login";
}

