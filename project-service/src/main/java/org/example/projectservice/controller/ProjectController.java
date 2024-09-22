package org.example.projectservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.dto.request.ProjectRequest;
import org.example.projectservice.dto.response.ProjectListResponse;
import org.example.projectservice.dto.response.ProjectResponse;
import org.example.projectservice.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_CEO')")
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody ProjectRequest project) {
        return ResponseEntity.ok(projectService.createNewProject(project));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_CEO')")
    @GetMapping
    public ResponseEntity<ProjectListResponse> getProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_CEO')")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable String projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_CEO')")
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProjectById(
            @PathVariable String projectId, ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProjectById(projectId, request));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_CEO')")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> deleteProjectById(
            @PathVariable String projectId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(projectService.deleteProjectById(projectId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_CEO')")
    @PutMapping("/{projectId}/close")
    public ResponseEntity<ProjectResponse> closeProject(
            @PathVariable String projectId) {
        return ResponseEntity.ok(projectService.closeProject(projectId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_CEO')")
    @PutMapping("/{projectId}/freeze")
    public ResponseEntity<ProjectResponse> freezeProject(
            @PathVariable String projectId) {
        return ResponseEntity.ok(projectService.freezeProject(projectId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_CEO')")
    @PutMapping("/{projectId}/open")
    public ResponseEntity<ProjectResponse> openProject(
            @PathVariable String projectId) {
        return ResponseEntity.ok(projectService.openProject(projectId));
    }
}
