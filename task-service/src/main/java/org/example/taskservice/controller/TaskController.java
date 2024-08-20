package org.example.taskservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable String id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTuskById(
            @PathVariable String id, @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTaskById(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTaskById(
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(taskService.deleteTaskById(id));
    }

}
