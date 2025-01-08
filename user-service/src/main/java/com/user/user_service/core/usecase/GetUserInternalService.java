package com.user.user_service.core.usecase;

import com.user.user_service.commons.dto.UserInternalResponse;
import com.user.user_service.commons.exception.RecordNotFoundException;
import com.user.user_service.commons.message.MessageSourceService;
import com.user.user_service.core.domain.UserStorage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetUserInternalService {

    @Autowired
    private MessageSourceService messageSourceService;
    @Autowired
    private UserStorage userStorage;

    public UserInternalResponse handle(String email) {
        var user = userStorage.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException(messageSourceService.getMessage(
                        "record.not.found"), false));

        return new UserInternalResponse(user.firstName(), user.lastName(), user.password(), user.gender(), user.getPassword(), user.mobile(),
                user.secretKey(), user.publicId(), user.dateOfBirth(), user.accountNumber(), user.bvn(), user.nin(), user.isValidateAccount(),
                user.isValidateBvn(), user.isValidateNin(), user.role());


    }
}
