package com.user.user_service.restspring.controller;

import an.awesome.pipelinr.Pipeline;
import com.user.user_service.commons.dto.SecretKey;
import com.user.user_service.core.dto.request.CreateNewUserRequest;
import com.user.user_service.core.dto.request.GetUserRequest;
import com.user.user_service.core.dto.request.LoginUserRequest;
import com.user.user_service.core.dto.request.ValidateKycRequest;
import com.user.user_service.core.dto.response.GetUserResponse;
import com.user.user_service.core.dto.response.LoginUserResponse;
import com.user.user_service.core.dto.response.ValidatekycResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.user.user_service.restspring.apis.UserAPI.CREATE_NEW_USER;
import static com.user.user_service.restspring.apis.UserAPI.GET_USER;
import static com.user.user_service.restspring.apis.UserAPI.LOGIN;
import static com.user.user_service.restspring.apis.UserAPI.USER;
import static com.user.user_service.restspring.apis.UserAPI.VALIDATE_BVN;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {

    private final Pipeline pipeline;

    @PostMapping(CREATE_NEW_USER)
    public SecretKey createNewUser(@RequestBody CreateNewUserRequest request) {
        return pipeline.send(request);
    }

    @PostMapping(VALIDATE_BVN)
    public ValidatekycResponse validateBVN(@RequestBody ValidateKycRequest request) {
        return pipeline.send(request);
    }

    @GetMapping(GET_USER)
    public GetUserResponse getUser(@PathVariable UUID publicId) {
        return pipeline.send(new GetUserRequest(publicId));
    }

    @PostMapping(LOGIN)
    public LoginUserResponse login(@RequestBody LoginUserRequest request) {
        return pipeline.send(request);
    }


}
