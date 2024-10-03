package org.example.taskservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskservice.client.ProjectFeignClient;
import org.example.taskservice.dto.response.ProjectResponse;
import org.example.taskservice.exception.NotFoundException;
import org.example.taskservice.exception.ProjectNotFoundException;
import org.example.taskservice.exception.ServiceUnAvailableException;
import org.example.taskservice.service.ProjectService;
import org.example.taskservice.utill.ExceptionMessages;
import org.springframework.stereotype.Service;

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
        log.info("Fallback NotFound was called due to : {}", e.getMessage());
        throw new ProjectNotFoundException(String.format(ExceptionMessages.PROJECT_NOT_FOUND_WITH_MESSAGE, e.getMessage()));
    }

    private ProjectResponse fall(Throwable e) {
        log.info("Fallback ServiceUnAvailable was called due to : {}", e.getMessage());
        throw new ServiceUnAvailableException(String.format(ExceptionMessages.SERVICE_IS_NOT_AVAILABLE_WITH_MESSAGE, e.getMessage()));
    }
}
