package org.example.projectservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.dto.ProjectRequest;
import org.example.projectservice.dto.ProjectResponse;
import org.example.projectservice.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {


    private final ProjectService projectService;


    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest project) {
        return ResponseEntity.ok(projectService.createNewProject(project));

    }
}
