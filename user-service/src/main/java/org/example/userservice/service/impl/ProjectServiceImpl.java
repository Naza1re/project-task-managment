package org.example.userservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectFeignClient projectFeignClient;

    @Override
    @CircuitBreaker(name = "project", fallbackMethod = "fallBackProjectService")
    public ProjectResponse getProjectById(String projectId) {
        return projectFeignClient.getProjectById(projectId);
    }

    private ProjectResponse fallBackProjectService(NotFoundException e) {
        throw new ProjectNotFoundException(String.format(PROJECT_NOT_FOUND_WITH_MESSAGE, e.getMessage()));
    }

    private ProjectResponse fallBackProjectService(Exception e) {
        throw new ServiceUnAvailableException(String.format(SERVICE_IS_NOT_AVAILABLE_WITH_MESSAGE, e.getMessage()));
    }
}
