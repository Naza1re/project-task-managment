package org.example.projectservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.dto.ProjectListResponse;
import org.example.projectservice.dto.ProjectRequest;
import org.example.projectservice.dto.ProjectResponse;
import org.example.projectservice.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody ProjectRequest project) {
        return ResponseEntity.ok(projectService.createNewProject(project));
    }

    @GetMapping
    public ResponseEntity<ProjectListResponse> getProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable String id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProjectById(
            @PathVariable String id, ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProjectById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponse> deleteProjectById(
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(projectService.deleteProjectById(id));

    }
}
