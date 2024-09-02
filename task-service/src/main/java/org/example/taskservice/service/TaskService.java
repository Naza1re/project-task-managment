package org.example.taskservice.service;

import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.TaskResponse;
import org.springframework.http.ResponseEntity;

public interface TaskService {
    TaskResponse createTask(TaskRequest request,String projectId);

    TaskResponse getTaskById(String id);

    TaskResponse updateTaskById(String id, TaskRequest request);

    TaskResponse deleteTaskById(String id);

    TaskResponse closeTask(String taskId);

    TaskResponse openTaskByTaskId(String taskId);

    TaskResponse assignTaskToUserWithId(String taskId, String userId);
}
