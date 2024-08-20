package org.example.taskservice.service;

import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.TaskResponse;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);

    TaskResponse getTaskById(String id);

    TaskResponse updateTaskById(String id, TaskRequest request);

    TaskResponse deleteTaskById(String id);
}
