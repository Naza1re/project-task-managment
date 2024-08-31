package org.example.userservice.service;

import org.example.userservice.config.security.User;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.dto.UserResponseList;
import org.springframework.security.oauth2.jwt.Jwt;

public interface UserService {
    UserResponse createUser(UserRequest request);

    UserResponse findUserById(String id);

    UserResponseList findAllUsers();

    UserResponse updateUserById(String id, UserRequest request);

    UserResponse deleteUserById(String id);

    User extractUserInfo(Jwt jwt);
}
