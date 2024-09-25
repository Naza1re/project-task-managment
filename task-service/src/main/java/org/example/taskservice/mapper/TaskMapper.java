package org.example.taskservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.TaskListResponse;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskMapper {
    private final ModelMapper modelMapper;

    public Task fromRequestToEntity(TaskRequest request) {
        return modelMapper.map(request, Task.class);
    }

    public TaskResponse fromEntityToResponse(Task task) {
        return modelMapper.map(task, TaskResponse.class);
    }

    public TaskListResponse fromEntityListToEntityListResponse(List<Task> tasksOfProject) {
        List<TaskResponse> taskResponses = tasksOfProject.stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new TaskListResponse(taskResponses);
    }
}
