package com.user.user_service.restspring.controller;

import com.user.user_service.commons.dto.UserInternalResponse;
import com.user.user_service.core.usecase.GetUserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.user.user_service.restspring.apis.UserInternalAPI.GET_INTERNAL_USER;
import static com.user.user_service.restspring.apis.UserInternalAPI.USER_INTERNAL;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_INTERNAL)
public class UserInternalController {

    private final GetUserInternalService userService;

    @GetMapping(GET_INTERNAL_USER)
    public UserInternalResponse getUser(@PathVariable String email) {
        return userService.handle(email);
    }


}
