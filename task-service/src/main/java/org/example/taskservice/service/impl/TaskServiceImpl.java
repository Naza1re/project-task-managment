package org.example.taskservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.client.ProjectFeignClient;
import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.ProjectResponse;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.exception.ProjectClosedException;
import org.example.taskservice.exception.TaskNotFoundException;
import org.example.taskservice.mapper.TaskMapper;
import org.example.taskservice.model.Task;
import org.example.taskservice.repository.TaskRepository;
import org.example.taskservice.service.TaskService;
import org.example.taskservice.utill.Constants;
import org.example.taskservice.utill.ExceptionMessages;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectFeignClient projectFeignClient;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponse createTask(TaskRequest request) {
        checkingProjectExistAndNotClosed(request.getProjectId());
        Task taskToSave = taskMapper.fromRequestToEntity(request);
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

    private void checkingProjectExistAndNotClosed(String projectId) {
        ProjectResponse response = projectFeignClient.getProjectById(projectId);
        if (response.getStatus().equals(Constants.CLOSED_PROJECT)) {
            throw new ProjectClosedException(String.format(ExceptionMessages.PROJECT_CLOSED_EXCEPTION, projectId));
        }
    }

    private Task getOrThrow(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format(ExceptionMessages.TASK_NOT_FPUND_EXCEPTION, id)));
    }
}
