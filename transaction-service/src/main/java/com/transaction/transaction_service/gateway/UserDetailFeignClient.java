package com.transaction.transaction_service.gateway;

import com.transaction.transaction_service.commons.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-service", url = "${user-service}")
public interface UserDetailFeignClient {

    @GetMapping("/internal/get-user/{email}")
    Optional<UserDto> findByEmail(@PathVariable String email);
}
