package org.example.userservice.service;

import org.example.userservice.security.model.User;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.dto.UserResponseList;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);

    UserResponse findUserById(String id);

    UserResponseList findAllUsers();

    UserResponse updateUserById(String id, UserRequest request);

    UserResponse deleteUserById(String id);

    User extractUserInfo(Jwt jwt);

    List<UserRepresentation> findAllUsersOfKyecloack();

}
