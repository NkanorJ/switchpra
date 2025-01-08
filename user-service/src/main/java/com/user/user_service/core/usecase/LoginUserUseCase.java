package com.user.user_service.core.usecase;

import an.awesome.pipelinr.Command;
import com.user.user_service.commons.config.JWTService;
import com.user.user_service.commons.dto.RateLimitingService;
import com.user.user_service.commons.exception.RateLimitException;
import com.user.user_service.commons.exception.UserRoleException;
import com.user.user_service.commons.message.MessageSourceService;
import com.user.user_service.core.domain.UserStorage;
import com.user.user_service.core.dto.request.LoginUserRequest;
import com.user.user_service.core.dto.response.LoginUserResponse;
import io.github.bucket4j.Bucket;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginUserUseCase implements Command.Handler<LoginUserRequest, LoginUserResponse>, Command<LoginUserResponse> {

    @Autowired
    private MessageSourceService messageSourceService;
    @Autowired
    private UserStorage userStorage;
    @Autowired
    private RateLimitingService rateLimitingService;

    @Autowired
    private AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    @Override
    public LoginUserResponse handle(LoginUserRequest request) {
        Bucket bucket = rateLimitingService.resolveBucket(request.email());

        if (!bucket.tryConsume(1))
            throw new RateLimitException(messageSourceService.getMessage("rate.trial.exceeded"), false);

        var user = userStorage.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        try {
           if (!user.email().equals(request.email())){
               throw  new UserRoleException(messageSourceService.getMessage("email.found"), false);
           }

            String token = jwtService.generateToken(user.email());
            return new LoginUserResponse(token, "Login successful");

        } catch (BadCredentialsException e) {
            return new LoginUserResponse(null, messageSourceService.getMessage("invalid.credentials.provided"));
        } catch (Exception e) {
            return new LoginUserResponse(null, messageSourceService.getMessage("login.failed"));
        }
    }
}












