package org.example.taskservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.TaskListResponse;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.mapper.TaskMapper;
import org.example.taskservice.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN')")
    @PostMapping("/{projectId}")
    public ResponseEntity<TaskResponse> createTaskForProject(
            @PathVariable String projectId,
            @RequestBody TaskRequest request,
            @AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskMapper
                        .fromEntityToResponse(taskService
                                .createTask(taskMapper
                                        .fromRequestToEntity(request), projectId, user)));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN')")
    @PutMapping("/assign/{taskId}/to/{userId}")
    public ResponseEntity<TaskResponse> assignTaskToProject
            (@PathVariable String taskId,
             @PathVariable String userId,
             @AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.ok(taskMapper
                .fromEntityToResponse(taskService
                        .assignTaskToUserWithId(taskId, userId, user)));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN')")
    @PutMapping("/close/{taskId}")
    public ResponseEntity<TaskResponse> closeTaskByTaskId(
            @PathVariable String taskId,
            @AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.ok(taskMapper
                .fromEntityToResponse(taskService
                        .closeTask(taskId, user)));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_ADMIN')")
    @PutMapping("/open/{taskId}")
    public ResponseEntity<TaskResponse> openTask(
            @PathVariable String taskId,
            @AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.ok(taskMapper
                .fromEntityToResponse(taskService
                        .openTaskByTaskId(taskId, user)));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable String id) {
        return ResponseEntity.ok(taskMapper
                .fromEntityToResponse(taskService.getTaskById(id)));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTuskById(
            @PathVariable String id,
            @RequestBody TaskRequest request,
            @AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.ok(taskMapper
                .fromEntityToResponse(taskService
                        .updateTaskById(id, taskMapper
                                .fromRequestToEntity(request), user)));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTaskById(
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(taskMapper
                        .fromEntityToResponse(taskService.deleteTaskById(id)));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_USER')")
    @GetMapping("/tasks/of/project/{projectId}")
    public ResponseEntity<TaskListResponse> getTasksByProjectId(
            @PathVariable String projectId) {
        return ResponseEntity.ok(taskMapper
                .fromEntityListToEntityListResponse(taskService
                        .findTasksOfProject(projectId)));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER')")
    @PutMapping("/refuse/{taskId}")
    public ResponseEntity<TaskResponse> refuseTaskByTaskId(
            @PathVariable String taskId,
            @AuthenticationPrincipal OAuth2User user
    ) {
        return ResponseEntity.ok(taskMapper
                .fromEntityToResponse(taskService
                        .refuseTaskByTaskId(taskId, user)));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_PROJECT_MANAGER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<TaskListResponse> getAllTasksOfUser(
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(taskMapper
                .fromEntityListToEntityListResponse(taskService
                        .findAllTasksOfUser(userId)));
    }

}
