package org.example.taskservice.client;

import org.example.taskservice.config.FeignClientConfiguration;
import org.example.taskservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "${service.user.name}",
        path = "${service.user.path}",
        configuration = FeignClientConfiguration.class)
public interface UserFeignClient {
    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable String userId);
}
