package org.example.userservice.service;

import org.example.userservice.dto.response.ProjectResponse;

public interface ProjectService {
    ProjectResponse getProjectById(String projectId);
}
