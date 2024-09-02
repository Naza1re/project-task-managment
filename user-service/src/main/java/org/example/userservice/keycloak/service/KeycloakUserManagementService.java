package org.example.userservice.keycloak.service;

import org.example.userservice.dto.request.UserRequest;

public interface KeycloakUserManagementService {
    void updateUser(String id, UserRequest request);

    void deleteUserById(String id);
}
