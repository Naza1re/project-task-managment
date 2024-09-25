package org.example.projectservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.client.CompanyFeignClient;
import org.example.projectservice.dto.response.CompanyResponse;
import org.example.projectservice.exception.ProjectNotFoundException;
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
    private final CompanyFeignClient companyFeignClient;

    @Override
    public Project createNewProject(Project request) {
        CompanyResponse companyResponse = companyFeignClient.getCompanyById(request.getCompanyId());
        request.setStatus(Status.OPEN);
        return projectRepository.save(request);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(String id) {
        return getOrThrow(id);
    }

    @Override
    public Project updateProjectById(String id, Project request) {
        Project project = getOrThrow(id);
        request.setId(project.getId());
        return projectRepository.save(request);
    }

    @Override
    public Project deleteProjectById(String id) {
        Project project = getOrThrow(id);
        projectRepository.delete(project);
        return project;
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
    public Project closeProject(String projectId) {
        Project project = getOrThrow(projectId);
        project.setStatus(Status.CLOSED);
        return projectRepository.save(project);
    }

    @Override
    public Project freezeProject(String projectId) {
        Project project = getOrThrow(projectId);
        project.setStatus(Status.FREEZE);
        return projectRepository.save(project);
    }

    @Override
    public Project openProject(String projectId) {
        Project project = getOrThrow(projectId);
        project.setStatus(Status.OPEN);
        return projectRepository.save(project);
    }

    private Project getOrThrow(String id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(String.format(ExceptionMessages.PROJECT_NOT_FOUND, id)));
    }
}
