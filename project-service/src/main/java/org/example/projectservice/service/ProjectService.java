package org.example.projectservice.service;

import org.example.projectservice.dto.ProjectListResponse;
import org.example.projectservice.dto.ProjectRequest;
import org.example.projectservice.dto.ProjectResponse;
import org.example.projectservice.security.model.User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ProjectService {
    ProjectResponse createNewProject(ProjectRequest project);

    ProjectListResponse getAllProjects();

    ProjectResponse getProjectById(String id);

    ProjectResponse updateProjectById(String id, ProjectRequest request);

    ProjectResponse deleteProjectById(String id);

    User extractUserInfo(Jwt jwt);
}
