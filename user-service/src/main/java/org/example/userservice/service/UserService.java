package org.example.userservice.service;

import org.example.userservice.dto.UserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.dto.UserResponseList;

public interface UserService {
    UserResponse createUser(UserRequest request);

    UserResponse findUserById(String id);

    UserResponseList findAllUsers();

    UserResponse updateUserById(String id, UserRequest request);

    UserResponse deleteUserById(String id);
}
