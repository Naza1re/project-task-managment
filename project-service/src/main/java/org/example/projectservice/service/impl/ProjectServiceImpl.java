package org.example.projectservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.dto.ProjectListResponse;
import org.example.projectservice.dto.ProjectRequest;
import org.example.projectservice.dto.ProjectResponse;
import org.example.projectservice.exception.ProjectNotFoundException;
import org.example.projectservice.mapper.ProjectMapper;
import org.example.projectservice.model.Project;
import org.example.projectservice.repository.ProjectRepository;
import org.example.projectservice.service.ProjectService;
import org.example.projectservice.utill.ExceptionMessages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponse createNewProject(ProjectRequest project) {
        Project project1 = projectMapper.fromRequestToEntity(project);
        System.out.println(project1);
        Project savedProject = projectRepository.save(project1);
        System.out.println(savedProject);
        return projectMapper.fromEntityToResponse(savedProject);
    }

    @Override
    public ProjectListResponse getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> projectResponses = projects
                .stream()
                .map(projectMapper::fromEntityToResponse)
                .toList();
        return new ProjectListResponse(projectResponses);
    }

    @Override
    public ProjectResponse getProjectById(String id) {
        Project project = getOrThrow(id);
        return projectMapper.fromEntityToResponse(project);
    }

    @Override
    public ProjectResponse updateProjectById(String id, ProjectRequest request) {
        Project project = getOrThrow(id);
        Project newProject = projectMapper.fromRequestToEntity(request);
        newProject.setId(project.getId());
        Project savedProject = projectRepository.save(newProject);
        return projectMapper.fromEntityToResponse(savedProject);
    }

    @Override
    public ProjectResponse deleteProjectById(String id) {
        Project project = getOrThrow(id);
        projectRepository.delete(project);
        return projectMapper.fromEntityToResponse(project);
    }

    private Project getOrThrow(String id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(String.format(ExceptionMessages.PROJECT_NOT_FOUND, id)));
    }
}
