package org.example.userservice.client;

import org.example.userservice.config.FeignClientConfiguration;
import org.example.userservice.dto.response.ProjectResponse;
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
