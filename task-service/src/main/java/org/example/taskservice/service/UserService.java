package org.example.taskservice.service;

import org.example.taskservice.dto.response.UserResponse;

public interface UserService {
    UserResponse getUser(String userId);
}
