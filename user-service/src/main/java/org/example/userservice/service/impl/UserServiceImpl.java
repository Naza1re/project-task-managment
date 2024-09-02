package org.example.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.request.UserRequest;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.dto.response.UserResponseList;
import org.example.userservice.exception.UserAlreadyExistByPhoneException;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.service.UserService;
import org.example.userservice.utill.ExceptionMessages;
import org.example.userservice.utill.KeycloakConstants;
import org.keycloak.admin.client.Keycloak;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.example.userservice.security.utill.SecurityConstants.*;
import static org.example.userservice.utill.KeycloakConstants.*;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final Keycloak keycloak;

    @Override
    public UserResponse createUser(OAuth2User oAuth2User) {
        UserRequest request = extractUserRequestFromOauth2User(oAuth2User);
        checkUserAlreadyExist(request);

        User user = mapper.fromRequestToEntity(request);
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
        return mapper.fromEntityToResponse(userRepository.save(updatedUser));
    }

    @Override
    public UserResponse deleteUserById(String id) {
        User user = getOrThrow(id);
        userRepository.delete(user);
        return mapper.fromEntityToResponse(user);
    }

    @Override
    public org.example.userservice.security.model.User extractUserInfo(Jwt jwt) {
        return org.example.userservice.security.model.User.builder()
                .phone(jwt.getClaim(PHONE))
                .id(UUID.fromString(jwt.getClaim(ID)))
                .companyId(jwt.getClaim(COMPANY_ID))
                .username(jwt.getClaim(USERNAME))
                .build();
    }

    private void checkUserAlreadyExist(UserRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new UserAlreadyExistByPhoneException(String.format(ExceptionMessages.USER_ALREADY_EXIST_BY_PHONE, request.getPhone()));
        }
    }

    private User getOrThrow(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, id)));
    }

    private UserRequest extractUserRequestFromOauth2User(OAuth2User user) {
        System.out.println((String) user.getAttribute(COMPANY_ID));
        System.out.println((String) user.getAttribute(USERNAME_ATTRIBUTE));
        System.out.println((String) user.getAttribute(PHONE_ATTRIBUTE));

        return UserRequest.builder()
                .username(user.getAttribute(KeycloakConstants.USERNAME_ATTRIBUTE))
                .phone(user.getAttribute(KeycloakConstants.PHONE_ATTRIBUTE))
                .companyId(user.getAttribute(COMPANY_ID))
                .id(Objects.requireNonNull(user.getAttribute(USER_ID)).toString())
                .build();
    }
}
