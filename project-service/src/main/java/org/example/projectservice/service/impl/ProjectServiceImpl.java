package org.example.projectservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.dto.ProjectRequest;
import org.example.projectservice.dto.ProjectResponse;
import org.example.projectservice.mapper.ProjectMapper;
import org.example.projectservice.model.Project;
import org.example.projectservice.repository.ProjectRepository;
import org.example.projectservice.service.ProjectService;
import org.springframework.stereotype.Service;

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
}
