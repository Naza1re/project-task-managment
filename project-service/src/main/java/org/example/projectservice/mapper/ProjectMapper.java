package org.example.projectservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.dto.ProjectRequest;
import org.example.projectservice.dto.ProjectResponse;
import org.example.projectservice.model.Project;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
