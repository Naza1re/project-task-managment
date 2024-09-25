package org.example.projectservice.service;

import org.example.projectservice.dto.response.ProjectListResponse;
import org.example.projectservice.dto.request.ProjectRequest;
import org.example.projectservice.dto.response.ProjectResponse;
import org.example.projectservice.model.Project;
import org.example.projectservice.security.model.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface ProjectService {
    Project createNewProject(Project project);

    List<Project> getAllProjects();

    Project getProjectById(String id);

    Project updateProjectById(String id, Project request);

    Project deleteProjectById(String id);

    User extractUserInfo(Jwt jwt);

    Project closeProject(String projectId);

    Project freezeProject(String projectId);

    Project openProject(String projectId);
}
