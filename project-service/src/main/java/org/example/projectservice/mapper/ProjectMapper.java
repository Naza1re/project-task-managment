package org.example.projectservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.dto.request.ProjectRequest;
import org.example.projectservice.dto.response.ProjectListResponse;
import org.example.projectservice.dto.response.ProjectResponse;
import org.example.projectservice.model.Project;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMapper {
    private final ModelMapper modelMapper;

    public Project fromRequestToEntity(ProjectRequest projectRequest) {
        return modelMapper.map(projectRequest, Project.class);
    }
    public ProjectResponse fromEntityToResponse(Project project) {
        return modelMapper.map(project, ProjectResponse.class);
    }

    public ProjectListResponse fromEntityListToEntityListResponse(List<Project> allProjects) {
        List<ProjectResponse> projectResponses = allProjects.stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new ProjectListResponse(projectResponses);
    }
}
