package org.example.userservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.client.ProjectFeignClient;
import org.example.userservice.dto.response.ProjectResponse;
import org.example.userservice.exception.NotFoundException;
import org.example.userservice.exception.ProjectNotFoundException;
import org.example.userservice.exception.ServiceUnAvailableException;
import org.example.userservice.service.ProjectService;
import org.springframework.stereotype.Service;

import static org.example.userservice.utill.ExceptionMessages.PROJECT_NOT_FOUND_WITH_MESSAGE;
import static org.example.userservice.utill.ExceptionMessages.SERVICE_IS_NOT_AVAILABLE_WITH_MESSAGE;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectFeignClient projectFeignClient;

    @Override
    @CircuitBreaker(name = "project", fallbackMethod = "fall")
    public ProjectResponse getProjectById(String projectId) {
        log.info("try to get project by id {}", projectId);
        return projectFeignClient.getProjectById(projectId);
    }

    private ProjectResponse fall(NotFoundException e) {
        log.info("Fallback NotFound was called due to {}", e.getMessage());
        throw new ProjectNotFoundException(String.format(PROJECT_NOT_FOUND_WITH_MESSAGE, e.getMessage()));
    }

    private ProjectResponse fall(Throwable e) {
        log.info("Fallback ServiceUnaVAILABLE was called due to {}", e.getMessage());
        throw new ServiceUnAvailableException(String.format(SERVICE_IS_NOT_AVAILABLE_WITH_MESSAGE, e.getMessage()));
    }
}
