package org.example.taskservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.dto.request.TaskRequest;
import org.example.taskservice.dto.response.TaskResponse;
import org.example.taskservice.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
