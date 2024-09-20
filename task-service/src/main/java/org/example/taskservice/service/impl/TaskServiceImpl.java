package org.example.taskservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.client.ProjectFeignClient;
import org.example.taskservice.client.UserFeignClient;
import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.ProjectResponse;
import org.example.taskservice.dto.response.TaskListResponse;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.dto.response.UserResponse;
import org.example.taskservice.exception.IllegalUserAccessException;
import org.example.taskservice.exception.TaskNotFoundException;
import org.example.taskservice.mapper.TaskMapper;
import org.example.taskservice.model.Task;
import org.example.taskservice.model.status.Status;
import org.example.taskservice.rabbit.TaskSender;
import org.example.taskservice.repository.TaskRepository;
import org.example.taskservice.security.model.User;
import org.example.taskservice.service.TaskService;
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
    private final ProjectFeignClient projectFeignClient;
    private final TaskMapper taskMapper;
    private final UserFeignClient userFeignClient;
    private final TaskSender taskSender;

    @Override
    public TaskResponse createTask(TaskRequest request, String projectId, OAuth2User user) {

        ProjectResponse response = projectFeignClient.getProjectById(projectId);
        Task taskToSave = taskMapper.fromRequestToEntity(request);

        taskToSave.setCreatedAt(LocalDateTime.now());
        taskToSave.setProjectId(response.getId());
        taskToSave.setStatus(Status.CREATED);
        taskToSave.setLastChangedBy(user.getName());

        Task savedTask = taskRepository.save(taskToSave);

        return taskMapper.fromEntityToResponse(savedTask);
    }

    @Override
    public TaskResponse getTaskById(String id) {
        Task task = getOrThrow(id);
        return taskMapper.fromEntityToResponse(task);
    }

    @Override
    public TaskResponse updateTaskById(String id, TaskRequest request, OAuth2User user) {
        Task task = getOrThrow(id);
        Task taskToSave = taskMapper.fromRequestToEntity(request);

        taskToSave.setId(task.getId());
        taskToSave.setLastUpdatedAt(LocalDateTime.now());
        taskToSave.setLastChangedBy(user.getName());

        Task savedTask = taskRepository.save(task);

        return taskMapper.fromEntityToResponse(savedTask);
    }

    @Override
    public TaskResponse deleteTaskById(String id) {
        Task task = getOrThrow(id);
        taskRepository.delete(task);
        return taskMapper.fromEntityToResponse(task);
    }

    @Override
    public TaskResponse closeTask(String taskId, OAuth2User user) {

        Task taskToSave = getOrThrow(taskId);

        taskToSave.setCloseAt(LocalDateTime.now());
        taskToSave.setStatus(Status.CLOSED);
        taskToSave.setLastChangedBy(user.getName());

        Task savedTask = taskRepository.save(taskToSave);
        return taskMapper.fromEntityToResponse(savedTask);
    }

    @Override
    public TaskResponse openTaskByTaskId(String taskId, OAuth2User user) {
        Task task = getOrThrow(taskId);

        task.setStatus(Status.OPEN);
        task.setLastChangedBy(user.getName());

        Task taskToSave = taskRepository.save(task);
        return taskMapper.fromEntityToResponse(taskToSave);
    }

    @Override
    public TaskResponse assignTaskToUserWithId(String taskId, String userId, OAuth2User user) {
        UserResponse userResponse = userFeignClient.getUser(userId);
        Task task = getOrThrow(taskId);

        task.setUserId(userResponse.getId());
        task.setStatus(Status.ASSIGNED);
        task.setLastChangedBy(user.getName());

        Task taskToSave = taskRepository.save(task);
        return taskMapper.fromEntityToResponse(taskToSave);
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
    public TaskListResponse findTasksOfProject(String projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);

        List<TaskResponse> taskResponses = tasks.stream()
                .map(taskMapper::fromEntityToResponse)
                .toList();

        return TaskListResponse.builder()
                .tasks(taskResponses)
                .build();
    }

    @Override
    public TaskResponse refuseTaskByTaskId(String taskId, OAuth2User user) {
        Task task = getOrThrow(taskId);
        refuseTaskCheckOpportunity(task, (User) user);
        task.setUserId(null);
        task.setLastChangedBy(user.getName());
        task.setStatus(Status.OPEN);

        Task taskToSave = taskRepository.save(task);
        return taskMapper.fromEntityToResponse(taskToSave);
    }

    @Override
    public TaskListResponse findAllTasksOfUser(String userId) {
        List<Task> tasksOfUser = taskRepository.findAllByUserId(userId);
        List<TaskResponse> taskResponses = tasksOfUser.stream()
                .map(taskMapper::fromEntityToResponse)
                .toList();
        return TaskListResponse.builder()
                .tasks(taskResponses)
                .build();
    }


    private Task getOrThrow(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format(ExceptionMessages.TASK_NOT_FPUND_EXCEPTION, id)));
    }

    private void refuseTaskCheckOpportunity(Task task, User user) {
        if(!user.getId().equals(task.getUserId())) {
            throw new IllegalUserAccessException(String.format(ExceptionMessages.ILLEGAL_USER_ACCESS_EXCEPTION,user.getId()));
        }

    }
}
