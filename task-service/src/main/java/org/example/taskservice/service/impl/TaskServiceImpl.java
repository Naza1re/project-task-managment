package org.example.taskservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.client.ProjectFeignClient;
import org.example.taskservice.client.UserFeignClient;
import org.example.taskservice.dto.response.ProjectResponse;
import org.example.taskservice.dto.response.UserResponse;
import org.example.taskservice.exception.IllegalUserAccessException;
import org.example.taskservice.exception.TaskNotFoundException;
import org.example.taskservice.model.Task;
import org.example.taskservice.model.status.Status;
import org.example.taskservice.repository.TaskRepository;
import org.example.taskservice.security.model.User;
import org.example.taskservice.service.ProjectService;
import org.example.taskservice.service.TaskService;
import org.example.taskservice.service.UserService;
import org.example.taskservice.utill.ExceptionMessages;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.example.taskservice.security.utill.SecurityConstants.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final UserService userService;

    @Override
    public Task createTask(Task request, String projectId, OAuth2User user) {

        ProjectResponse response = projectService.getProjectById(projectId);

        request.setCreatedAt(LocalDateTime.now());
        request.setProjectId(response.getId());
        request.setStatus(Status.CREATED);
        request.setLastChangedBy(user.getName());

        return taskRepository.save(request);
    }

    @Override
    public Task getTaskById(String id) {
        return getOrThrow(id);
    }

    @Override
    public Task updateTaskById(String id, Task request, OAuth2User user) {
        Task task = getOrThrow(id);

        request.setId(task.getId());
        request.setLastUpdatedAt(LocalDateTime.now());
        request.setLastChangedBy(user.getName());
        return taskRepository.save(task);
    }

    @Override
    public Task deleteTaskById(String id) {
        Task task = getOrThrow(id);
        taskRepository.delete(task);
        return task;
    }

    @Override
    public Task closeTask(String taskId, OAuth2User user) {

        Task taskToSave = getOrThrow(taskId);

        taskToSave.setCloseAt(LocalDateTime.now());
        taskToSave.setStatus(Status.CLOSED);
        taskToSave.setLastChangedBy(user.getName());

        return taskRepository.save(taskToSave);
    }

    @Override
    public Task openTaskByTaskId(String taskId, OAuth2User user) {
        Task task = getOrThrow(taskId);

        task.setStatus(Status.OPEN);
        task.setLastChangedBy(user.getName());

        return taskRepository.save(task);
    }

    @Override
    public Task assignTaskToUserWithId(String taskId, String userId, OAuth2User user) {
        UserResponse userResponse = userService.getUser(userId);
        Task task = getOrThrow(taskId);

        task.setUserId(userResponse.getId());
        task.setStatus(Status.ASSIGNED);
        task.setLastChangedBy(user.getName());

        return taskRepository.save(task);
    }

    @Override
    public User extractUserInfo(Jwt jwt) {
        return User.builder()
                .surname(jwt.getClaim(FAMILY_NAME))
                .name(jwt.getClaim(GIVEN_NAME))
                .id(UUID.fromString(jwt.getClaim(ID)))
                .email(jwt.getClaim(EMAIL))
                .username(jwt.getClaim(USERNAME))
                .build();
    }

    @Override
    public List<Task> findTasksOfProject(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public Task refuseTaskByTaskId(String taskId, OAuth2User user) {
        Task task = getOrThrow(taskId);
        refuseTaskCheckOpportunity(task, (User) user);
        task.setUserId(null);
        task.setLastChangedBy(user.getName());
        task.setStatus(Status.OPEN);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAllTasksOfUser(String userId) {
       return taskRepository.findAllByUserId(userId);
    }


    private Task getOrThrow(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format(ExceptionMessages.TASK_NOT_FPUND_EXCEPTION, id)));
    }

    private void refuseTaskCheckOpportunity(Task task, User user) {
        if (!user.getId().equals(task.getUserId())) {
            throw new IllegalUserAccessException(String.format(ExceptionMessages.ILLEGAL_USER_ACCESS_EXCEPTION, user.getId()));
        }

    }
}
