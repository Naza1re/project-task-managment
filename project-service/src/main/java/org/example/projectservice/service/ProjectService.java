package org.example.projectservice.service;

import org.example.projectservice.dto.ProjectListResponse;
import org.example.projectservice.dto.ProjectRequest;
import org.example.projectservice.dto.ProjectResponse;

public interface ProjectService {
    ProjectResponse createNewProject(ProjectRequest project);

    ProjectListResponse getAllProjects();

    ProjectResponse getProjectById(String id);

    ProjectResponse updateProjectById(String id, ProjectRequest request);

    ProjectResponse deleteProjectById(String id);
}
