package org.example.taskservice.service;

import org.example.taskservice.dto.response.TaskListResponse;
import org.example.taskservice.model.Task;
import org.example.taskservice.security.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface TaskService {
    Task createTask(Task request, String projectId, OAuth2User user);

    Task getTaskById(String id);

    Task updateTaskById(String id, Task request, OAuth2User user);

    Task deleteTaskById(String id);

    Task closeTask(String taskId, OAuth2User user);

    Task openTaskByTaskId(String taskId, OAuth2User user);

    Task assignTaskToUserWithId(String taskId, String userId, OAuth2User user);

    User extractUserInfo(Jwt jwt);

    List<Task> findTasksOfProject(String projectId);

    Task refuseTaskByTaskId(String taskId, OAuth2User user);

    List<Task> findAllTasksOfUser(String userId);

}
