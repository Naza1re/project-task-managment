package org.example.projectservice.service;

import org.example.projectservice.dto.ProjectRequest;
import org.example.projectservice.dto.ProjectResponse;
import org.example.projectservice.model.Project;

public interface ProjectService {
    ProjectResponse createNewProject(ProjectRequest project);
}
