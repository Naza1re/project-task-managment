package org.example.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.response.ProjectResponse;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.keycloak.service.KeycloakUserManagementService;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.service.ProjectService;
import org.example.userservice.service.UserService;
import org.example.userservice.utill.KeycloakConstants;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.example.userservice.security.utill.SecurityConstants.*;
import static org.example.userservice.utill.ExceptionMessages.USER_NOT_FOUND;
import static org.example.userservice.utill.KeycloakConstants.ROLE_MANAGER_PROJECT;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakUserManagementService keycloakUserManagementService;
    private final ProjectService projectService;

    @Override
    public User createUser(OAuth2User oAuth2User, User request) {

        setAdditionFieldsForOauth2User(request, oAuth2User);

        return userRepository.save(request);
    }

    @Override
    public User findUserById(String id) {
        return getOrThrow(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserById(String id, User request) {
        User user = getOrThrow(id);

        request.setId(user.getId());

        keycloakUserManagementService.updateUser(id, request);
        return userRepository.save(request);
    }

    @Override
    public User deleteUserById(String id) {
        User user = getOrThrow(id);

        keycloakUserManagementService.deleteUserById(id);
        userRepository.delete(user);
        return user;
    }

    @Override
    public org.example.userservice.security.model.User extractUserInfo(Jwt jwt) {
        return org.example.userservice.security.model.User.builder()
                .surname(jwt.getClaim(FAMILY_NAME))
                .name(jwt.getClaim(GIVEN_NAME))
                .id(UUID.fromString(jwt.getClaim(ID)))
                .email(jwt.getClaim(EMAIL))
                .username(jwt.getClaim(USERNAME))
                .build();
    }

    @Override
    public User addProjectToUser(String userId, String projectId) {
        ProjectResponse response = projectService.getProjectById(projectId);
        User user = getOrThrow(userId);

        List<String> projects = user.getProjects();
        if (projects != null && !projects.isEmpty()) {
            projects.add(response.getId());
        } else {
            projects = new ArrayList<>();
            projects.add(response.getId());
        }
        user.setProjects(projects);

        return userRepository.save(user);
    }

    @Override
    public List<User> findAllManagerAndUsers() {
        return userRepository.findAllByRoles(ROLE_MANAGER_PROJECT);
    }

    private User getOrThrow(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));
    }

    private void setAdditionFieldsForOauth2User(User user, OAuth2User oAuth2User) {
        user.setEmail(oAuth2User.getAttribute(EMAIL));
        user.setId(Objects.requireNonNull(oAuth2User.getAttribute(KeycloakConstants.USER_ID)).toString());
        user.setUsername(oAuth2User.getAttribute(EMAIL));

    }
}
