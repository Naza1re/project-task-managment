package org.example.taskservice.client;

import org.example.taskservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient
public interface UserFeignClient {
    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable String userId);
}
