package com.user.user_service.core.usecase;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.user_service.commons.dto.PendingRequestDto;
import com.user.user_service.commons.dto.RateLimitingService;
import com.user.user_service.commons.dto.SecretKey;
import com.user.user_service.commons.dto.UserDto;
import com.user.user_service.commons.enumeration.Gender;
import com.user.user_service.commons.enumeration.RequestStatus;
import com.user.user_service.commons.enumeration.RequestType;
import com.user.user_service.commons.enumeration.Role;
import com.user.user_service.commons.exception.PendingDualRequestException;
import com.user.user_service.commons.exception.PhoneNumberException;
import com.user.user_service.commons.exception.RateLimitException;
import com.user.user_service.commons.exception.UserRegistrationException;
import com.user.user_service.commons.message.MessageSourceService;
import com.user.user_service.core.domain.PendingRequestStorage;
import com.user.user_service.core.domain.UserStorage;
import com.user.user_service.core.dto.request.CreateNewUserRequest;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static com.user.user_service.commons.validation.PhoneNumberValidator.convertToInternationalFormat;

@Component
@AllArgsConstructor
public class CreateNewUserUseCase implements Command.Handler<CreateNewUserRequest, SecretKey>, Command<SecretKey> {

    @Autowired
    private MessageSourceService messageSourceService;
    @Autowired
    private UserStorage userStorage;
    @Autowired
    private RateLimitingService rateLimitingService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PendingRequestStorage pendingRequestStorage;

    @Override
    public SecretKey handle(CreateNewUserRequest request) {
        String formattedMobile = convertToInternationalFormat(request.phoneNumber());

        if (userStorage.findByPhoneNumber(request.phoneNumber()).isPresent())
            throw new PhoneNumberException(messageSourceService.getMessage(
                    "a.user.already.exists.with.that.phone.number.please.login"), false);

        if (userStorage.findByEmail(request.email()).isPresent())
            throw new UserRegistrationException(String.format(
                    messageSourceService.getMessage("a.user.already.exists.with.that.email.address.please.login"),
                    request.email()), false);

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Bucket bucket = rateLimitingService.resolveIPAddress(httpServletRequest.getRemoteAddr());
        if (!bucket.tryConsume(1))
            throw new RateLimitException(messageSourceService.getMessage("rate.trial.exceeded"), false);

        String encryptedPassword = bCryptPasswordEncoder.encode(request.password());

        String[] secretKey = getUserSecretKey(request.email());
        var publicId = UUID.randomUUID();
        SecretKey response = userStorage.createUser(new UserDto(UUID.randomUUID(), request.firstName(), request.lastName(),
                Gender.valueOf(request.gender()), encryptedPassword, request.email(), formattedMobile, secretKey[1], UUID.randomUUID(),
                request.dateOfBirth(), String.valueOf(new Random().nextInt(1000000000)), request.bvn(), request.nin(),
                false, false, false, Role.USER));

        String additionalInfoJson = serializeToJson(request);

        createPendingRequest(request, publicId, additionalInfoJson);

        return new SecretKey(request.email(), secretKey[0], "Client Secret", response.publicId());

    }

    private String[] getUserSecretKey(String email) {
        String[] secretKey = new String[2];
        secretKey[0] = DigestUtils.sha512Hex(String.format("%s-%s-%s", RandomStringUtils.randomAlphabetic(6), System.nanoTime(), email));
        secretKey[0] = StringUtils.substring(secretKey[0], 0, 60);
        secretKey[1] = bCryptPasswordEncoder.encode(secretKey[0]);
        return secretKey;
    }

    private void createPendingRequest(CreateNewUserRequest request, UUID publicId, String additionalInfoJson) {

        pendingRequestStorage.getPendingRequest(publicId)
                .orElseThrow(() -> new PendingDualRequestException(messageSourceService.getMessage("dual.request"), false));

        pendingRequestStorage.createPendingRequest(new PendingRequestDto(publicId, publicId,
                RequestType.KYC_VALIDATION, additionalInfoJson, request.email(), RequestStatus.PENDING));

    }

    private String serializeToJson(CreateNewUserRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing settlement mappings to JSON", e);
        }
    }
}
