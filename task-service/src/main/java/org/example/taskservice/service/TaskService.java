package org.example.taskservice.service;

import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.TaskListResponse;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.security.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

public interface TaskService {
    TaskResponse createTask(TaskRequest request,String projectId);

    TaskResponse getTaskById(String id);

    TaskResponse updateTaskById(String id, TaskRequest request);

    TaskResponse deleteTaskById(String id);

    TaskResponse closeTask(String taskId);

    TaskResponse openTaskByTaskId(String taskId);

    TaskResponse assignTaskToUserWithId(String taskId, String userId);

    User extractUserInfo(Jwt jwt);

    TaskListResponse findTasksOfProject(String projectId);
}
