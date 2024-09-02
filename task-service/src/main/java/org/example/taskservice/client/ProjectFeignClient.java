package org.example.taskservice.client;

import org.example.taskservice.config.FeignClientConfiguration;
import org.example.taskservice.dto.response.ProjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${service.project.name}",
        path = "${service.project.path}",
        configuration = FeignClientConfiguration.class)
public interface ProjectFeignClient {

    @GetMapping("/{id}")
    ProjectResponse getProjectById(@PathVariable String id);
}
