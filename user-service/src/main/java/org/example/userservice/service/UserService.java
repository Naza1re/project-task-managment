package org.example.userservice.service;

import org.example.userservice.dto.request.UserRequest;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.dto.response.UserResponseList;
import org.example.userservice.security.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface UserService {
    UserResponse createUser(OAuth2User user, UserRequest request);

    UserResponse findUserById(String id);

    UserResponseList findAllUsers();

    UserResponse updateUserById(String id, UserRequest request);

    UserResponse deleteUserById(String id);

    User extractUserInfo(Jwt jwt);

    UserResponse addProjectToUser(String userId, String projectId);
}
