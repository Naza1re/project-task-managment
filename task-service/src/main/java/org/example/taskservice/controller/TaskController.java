package org.example.taskservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN')")
    @PostMapping("/{projectId}")
    public ResponseEntity<TaskResponse> createTaskForProject(
            @PathVariable String projectId,
            @RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(request,projectId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN')")
    @PutMapping("/assign/{taskId}/to/{userId}")
    public ResponseEntity<TaskResponse> assignTaskToProject
            (@PathVariable String taskId,
             @PathVariable String userId) {
        return ResponseEntity.ok(taskService.assignTaskToUserWithId(taskId,userId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN')")
    @PutMapping("/close/{taskId}")
    public ResponseEntity<TaskResponse> closeTaskBYTaskId(
            @PathVariable String taskId) {
        return ResponseEntity.ok(taskService.closeTask(taskId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_ADMIN')")
    @PutMapping("/open/{taskId}")
    public ResponseEntity<TaskResponse> openTask(
            @PathVariable String taskId) {
        return ResponseEntity.ok(taskService.openTaskByTaskId(taskId));
    }


    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable String id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTuskById(
            @PathVariable String id, @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTaskById(id,request));
    }

    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTaskById(
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(taskService.deleteTaskById(id));
    }


}
