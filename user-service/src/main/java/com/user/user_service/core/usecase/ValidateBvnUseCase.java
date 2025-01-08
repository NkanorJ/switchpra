package com.user.user_service.core.usecase;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.user_service.commons.dto.UserDto;
import com.user.user_service.commons.enumeration.RequestStatus;
import com.user.user_service.commons.exception.PendingDualRequestException;
import com.user.user_service.commons.exception.RecordNotFoundException;
import com.user.user_service.commons.exception.UserRoleException;
import com.user.user_service.commons.message.MessageSourceService;
import com.user.user_service.core.domain.PendingRequestStorage;
import com.user.user_service.core.domain.UserStorage;
import com.user.user_service.core.dto.request.ValidateKycRequest;
import com.user.user_service.core.dto.response.ValidatekycResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidateBvnUseCase implements Command.Handler<ValidateKycRequest, ValidatekycResponse>, Command<ValidatekycResponse> {

    @Autowired
    private MessageSourceService messageSourceService;
    @Autowired
    private UserStorage userStorage;
    private final PendingRequestStorage pendingRequestStorage;
    private final ObjectMapper objectMapper;

    @Override
    public ValidatekycResponse handle(ValidateKycRequest request) {

        var pendingRequest = pendingRequestStorage.getPendingRequest(request.publicId())
                .orElseThrow(() -> new PendingDualRequestException(messageSourceService.getMessage("dual.request"), false));

        var user = userStorage.findByPublicId(request.publicId())
                .orElseThrow(() -> new RecordNotFoundException(messageSourceService.getMessage("record.not.found"), false));

        if (user.role().equals("USER"))
            throw new UserRoleException(messageSourceService.getMessage("user.is.not.admin"), false);

        if (!RequestStatus.PENDING.equals(pendingRequest.status()))
            throw new PendingDualRequestException(messageSourceService.getMessage("request.treated"), false);

        if (RequestStatus.APPROVED.equals(RequestStatus.valueOf(request.status()))) {

            try {
                var dto = objectMapper.readValue(pendingRequest.additionalInfo(), UserDto.class);

                userStorage.updateUser(dto);

                pendingRequestStorage.updatePendingRequest(request.publicId(), RequestStatus.valueOf(request.status()));

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
        return new ValidatekycResponse("KYC request handled");
    }
}
