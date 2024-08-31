package org.example.taskservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.client.ProjectFeignClient;
import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.ProjectResponse;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.exception.TaskNotFoundException;
import org.example.taskservice.mapper.TaskMapper;
import org.example.taskservice.model.Task;
import org.example.taskservice.model.status.Status;
import org.example.taskservice.repository.TaskRepository;
import org.example.taskservice.service.TaskService;
import org.example.taskservice.utill.ExceptionMessages;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectFeignClient projectFeignClient;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponse createTask(TaskRequest request, String projectId) {

        ProjectResponse response = projectFeignClient.getProjectById(projectId);

        Task taskToSave = taskMapper.fromRequestToEntity(request);
        taskToSave.setCreatedAt(LocalDateTime.now());
        taskToSave.setProjectId(response.getId());
        taskToSave.setStatus(Status.CREATED);
        Task savedTask = taskRepository.save(taskToSave);

        return taskMapper.fromEntityToResponse(savedTask);
    }

    @Override
    public TaskResponse getTaskById(String id) {
        Task task = getOrThrow(id);
        return taskMapper.fromEntityToResponse(task);
    }

    @Override
    public TaskResponse updateTaskById(String id, TaskRequest request) {
        Task task = getOrThrow(id);
        Task taskToSave = taskMapper.fromRequestToEntity(request);
        taskToSave.setId(task.getId());
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
    public TaskResponse closeTask(String taskId) {

        Task task = getOrThrow(taskId);
        task.setCloseAt(LocalDateTime.now());
        task.setStatus(Status.CLOSED);
        Task taskToSave = taskRepository.save(task);

        return taskMapper.fromEntityToResponse(taskToSave);
    }

    @Override
    public TaskResponse openTaskByTaskId(String taskId) {
        Task task = getOrThrow(taskId);
        task.setStatus(Status.OPEN);
        Task taskToSave = taskRepository.save(task);
        return taskMapper.fromEntityToResponse(taskToSave);
    }


    private Task getOrThrow(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format(ExceptionMessages.TASK_NOT_FPUND_EXCEPTION, id)));
    }
}
