package com.user.user_service.core.usecase;

import an.awesome.pipelinr.Command;
import com.user.user_service.commons.exception.RecordNotFoundException;
import com.user.user_service.commons.message.MessageSourceService;
import com.user.user_service.core.domain.UserStorage;
import com.user.user_service.core.dto.request.GetUserRequest;
import com.user.user_service.core.dto.response.GetUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetUseCase implements Command.Handler<GetUserRequest, GetUserResponse>, Command<GetUserResponse> {

    @Autowired
    private MessageSourceService messageSourceService;
    @Autowired
    private UserStorage userStorage;

    @Override
    public GetUserResponse handle(GetUserRequest getUserRequest) {
        var user = userStorage.findByPublicId(getUserRequest.publicId())
                .orElseThrow(() -> new RecordNotFoundException(messageSourceService.getMessage(
                        "record.not.found"), false));


        return new GetUserResponse(user.firstName().concat(" ").concat(user.lastName()), user.email(), user.gender().toString(),
                user.dateOfBirth(), user.accountNumber());

    }
}
