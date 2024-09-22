package org.example.projectservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.client.CompanyFeignClient;
import org.example.projectservice.dto.request.ProjectRequest;
import org.example.projectservice.dto.response.CompanyResponse;
import org.example.projectservice.dto.response.ProjectListResponse;
import org.example.projectservice.dto.response.ProjectResponse;
import org.example.projectservice.exception.ProjectNotFoundException;
import org.example.projectservice.mapper.ProjectMapper;
import org.example.projectservice.model.Project;
import org.example.projectservice.model.status.Status;
import org.example.projectservice.repository.ProjectRepository;
import org.example.projectservice.security.model.User;
import org.example.projectservice.service.ProjectService;
import org.example.projectservice.utill.ExceptionMessages;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.example.projectservice.security.utill.SecurityConstants.*;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final CompanyFeignClient companyFeignClient;

    @Override
    public ProjectResponse createNewProject(ProjectRequest request) {
        CompanyResponse companyResponse = companyFeignClient.getCompanyById(request.getCompanyId());
        Project project = projectMapper.fromRequestToEntity(request);
        project.setStatus(Status.OPEN);
        Project savedProject = projectRepository.save(project);
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

    @Override
    public User extractUserInfo(Jwt jwt) {
        return User.builder()
                .surname(jwt.getClaim(FAMILY_NAME))
                .name(jwt.getClaim(GIVEN_NAME))
                .id(UUID.fromString(jwt.getClaim(ID)))
                .email(jwt.getClaim(EMAIL))
                .username(jwt.getClaim(USERNAME))
                .build();
    }

    @Override
    public ProjectResponse closeProject(String projectId) {
        Project project = getOrThrow(projectId);
        project.setStatus(Status.CLOSED);
        Project savedProject = projectRepository.save(project);
        return projectMapper.fromEntityToResponse(savedProject);
    }

    @Override
    public ProjectResponse freezeProject(String projectId) {
        Project project = getOrThrow(projectId);
        project.setStatus(Status.FREEZE);
        Project savedProject = projectRepository.save(project);
        return projectMapper.fromEntityToResponse(savedProject);
    }

    @Override
    public ProjectResponse openProject(String projectId) {
        Project project = getOrThrow(projectId);
        project.setStatus(Status.OPEN);
        Project savedProject = projectRepository.save(project);
        return projectMapper.fromEntityToResponse(savedProject);
    }

    private Project getOrThrow(String id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(String.format(ExceptionMessages.PROJECT_NOT_FOUND, id)));
    }
}
