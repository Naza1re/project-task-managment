package org.example.userservice.keycloak.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.keycloak.service.KeycloakUserManagementService;
import org.example.userservice.model.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakUserManagementServiceImpl implements KeycloakUserManagementService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public void updateUser(String id, User request) {
        UsersResource usersResource = keycloak.realm(realm).users();
        UserResource userResource = usersResource.get(id);
        UserRepresentation user = userResource.toRepresentation();

        Map<String, List<String>> attributes = user.getAttributes();
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        user.setAttributes(attributes);

        userResource.update(user);
    }

    @Override
    public void deleteUserById(String id) {
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.delete(id);
    }
}
