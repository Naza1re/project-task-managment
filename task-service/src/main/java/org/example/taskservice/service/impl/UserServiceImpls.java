package org.example.taskservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskservice.client.UserFeignClient;
import org.example.taskservice.dto.response.ProjectResponse;
import org.example.taskservice.dto.response.UserResponse;
import org.example.taskservice.exception.NotFoundException;
import org.example.taskservice.exception.ProjectNotFoundException;
import org.example.taskservice.exception.ServiceUnAvailableException;
import org.example.taskservice.service.UserService;
import org.example.taskservice.utill.ExceptionMessages;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpls implements UserService {
    private final UserFeignClient userFeignClient;

    @Override
    @CircuitBreaker(name = "user", fallbackMethod = "fall")
    public UserResponse getUser(String userId) {
        log.info("try to get user with id {} ", userId);
        return userFeignClient.getUser(userId);
    }

    private ProjectResponse fall(NotFoundException e) {
        log.info("Fallback NotFound was called due to : {}", e.getMessage());
        throw new ProjectNotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND_EXCEPTION, e.getMessage()));
    }

    private ProjectResponse fall(Throwable e) {
        log.info("Fallback ServiceUnAvailable was called due to : {}", e.getMessage());
        throw new ServiceUnAvailableException(String.format(ExceptionMessages.SERVICE_IS_NOT_AVAILABLE_WITH_MESSAGE, e.getMessage()));
    }
}
