package org.example.userservice.keycloak.service;

import org.example.userservice.model.User;

public interface KeycloakUserManagementService {
    void updateUser(String id, User request);

    void deleteUserById(String id);
}
