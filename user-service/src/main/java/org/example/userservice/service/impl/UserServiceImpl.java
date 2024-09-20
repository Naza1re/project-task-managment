package org.example.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.client.ProjectFeignClient;
import org.example.userservice.dto.request.UserRequest;
import org.example.userservice.dto.response.ProjectResponse;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.dto.response.UserResponseList;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.keycloak.service.KeycloakUserManagementService;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
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
    private final UserMapper mapper;
    private final KeycloakUserManagementService keycloakUserManagementService;
    private final ProjectFeignClient projectFeignClient;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(OAuth2User oAuth2User, UserRequest request) {


        User user = mapper.fromRequestToEntity(request);
        setAdditionFieldsForOauth2User(user, oAuth2User);
        User savedUser = userRepository.save(user);

        return mapper.fromEntityToResponse(savedUser);
    }

    @Override
    public UserResponse findUserById(String id) {
        User user = getOrThrow(id);
        return mapper.fromEntityToResponse(user);
    }

    @Override
    public UserResponseList findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream()
                .map(mapper::fromEntityToResponse)
                .toList();
        return UserResponseList.builder()
                .userResponseList(userResponses)
                .build();
    }

    @Override
    public UserResponse updateUserById(String id, UserRequest request) {
        User user = getOrThrow(id);
        User updatedUser = mapper.fromRequestToEntity(request);
        updatedUser.setId(user.getId());
        keycloakUserManagementService.updateUser(id, request);
        return mapper.fromEntityToResponse(userRepository.save(updatedUser));
    }

    @Override
    public UserResponse deleteUserById(String id) {
        User user = getOrThrow(id);
        keycloakUserManagementService.deleteUserById(id);
        userRepository.delete(user);
        return mapper.fromEntityToResponse(user);
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
    public UserResponse addProjectToUser(String userId, String projectId) {
        ProjectResponse response = projectFeignClient.getProjectById(projectId);
        User user = getOrThrow(userId);

        List<String> projects = user.getProjects();
        if (projects != null && !projects.isEmpty()) {
            projects.add(response.getId());
        } else {
            projects = new ArrayList<>();
            projects.add(response.getId());
        }
        user.setProjects(projects);

        User userToSave = userRepository.save(user);

        return mapper.fromEntityToResponse(userToSave);
    }

    @Override
    public UserResponseList findAllManagerAndUsers() {
        List<User> managerslist = userRepository.findAllByRoles(ROLE_MANAGER_PROJECT);
        List<UserResponse> managersResponseList = managerslist.stream()
                .map(mapper::fromEntityToResponse)
                .toList();

        return UserResponseList.builder()
                .userResponseList(managersResponseList)
                .build();
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
