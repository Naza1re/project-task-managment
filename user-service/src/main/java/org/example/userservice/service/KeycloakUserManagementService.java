package org.example.userservice.service;

import org.example.userservice.dto.request.UserRequest;

public interface KeycloakUserManagementService {
    void updateUser(String id, UserRequest request);

    void deleteUserById(String id);
}
