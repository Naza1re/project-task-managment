package org.example.userservice.service;

import org.example.userservice.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface UserService {
    org.example.userservice.model.User createUser(OAuth2User user, org.example.userservice.model.User request);

    User findUserById(String id);

    List<User> findAllUsers();

    User updateUserById(String id, User request);

    User deleteUserById(String id);

    org.example.userservice.security.model.User extractUserInfo(Jwt jwt);

    User addProjectToUser(String userId, String projectId);

    List<User> findAllManagerAndUsers();
}
