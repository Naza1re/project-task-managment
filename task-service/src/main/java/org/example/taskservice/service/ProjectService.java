package org.example.taskservice.service;

import org.example.taskservice.dto.response.ProjectResponse;

public interface ProjectService {
    ProjectResponse getProjectById(String projectId);
}
