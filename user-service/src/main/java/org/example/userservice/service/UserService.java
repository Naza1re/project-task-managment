package org.example.userservice.service;

import org.example.userservice.security.model.User;
import org.example.userservice.dto.request.UserRequest;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.dto.response.UserResponseList;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface UserService {
    UserResponse createUser(OAuth2User user);

    UserResponse findUserById(String id);

    UserResponseList findAllUsers();

    UserResponse updateUserById(String id, UserRequest request);

    UserResponse deleteUserById(String id);

    User extractUserInfo(Jwt jwt);


}
