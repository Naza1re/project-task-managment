package org.example.taskservice.service;

import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.TaskListResponse;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.security.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface TaskService {
    TaskResponse createTask(TaskRequest request, String projectId, OAuth2User user);

    TaskResponse getTaskById(String id);

    TaskResponse updateTaskById(String id, TaskRequest request, OAuth2User user);

    TaskResponse deleteTaskById(String id);

    TaskResponse closeTask(String taskId, OAuth2User user);

    TaskResponse openTaskByTaskId(String taskId, OAuth2User user);

    TaskResponse assignTaskToUserWithId(String taskId, String userId, OAuth2User user);

    User extractUserInfo(Jwt jwt);

    TaskListResponse findTasksOfProject(String projectId);

    TaskResponse refuseTaskByTaskId(String taskId, OAuth2User user);

    TaskListResponse findAllTasksOfUser(String userId);

}
