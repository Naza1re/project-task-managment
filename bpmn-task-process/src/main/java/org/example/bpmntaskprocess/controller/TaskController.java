package org.example.bpmntaskprocess.controller;

import lombok.RequiredArgsConstructor;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/task-bpmn")
@RequiredArgsConstructor
public class TaskController {

    private final RuntimeService runtimeService;

    @PostMapping("/create")
    public String createTask(@RequestParam String taskId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("taskId", taskId);

        runtimeService.startProcessInstanceByKey("createTaskProcess", variables);

        return "Task process started with task name: " + taskId;
    }
}
