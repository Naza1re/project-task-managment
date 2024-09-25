package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.request.UserRequest;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.dto.response.UserResponseList;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @AuthenticationPrincipal OAuth2User user,
            @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper
                        .fromEntityToResponse(userService
                                .createUser(user, userMapper.fromRequestToEntity(request))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(userMapper
                .fromEntityToResponse(userService
                        .findUserById(id)));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<UserResponseList> findAllUsers() {
        return ResponseEntity.ok(userMapper
                .fromEntityListToResponseList(userService.findAllUsers()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String id, @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userMapper
                .fromEntityToResponse(userService
                        .updateUserById(id, userMapper
                                .fromRequestToEntity(request))));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(
            @PathVariable String id
    ) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(userMapper
                        .fromEntityToResponse(userService.deleteUserById(id)));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{userId}/add/project/{projectId}")
    public ResponseEntity<UserResponse> addProjectToUser(
            @PathVariable String projectId,
            @PathVariable String userId) {
        return ResponseEntity.ok(userMapper
                .fromEntityToResponse(userService
                        .addProjectToUser(userId, projectId)));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER')")
    @GetMapping("/admins")
    public ResponseEntity<UserResponseList> getAllAdminsAndProjectManagers() {
        return ResponseEntity.ok(userMapper
                .fromEntityListToResponseList(userService.findAllManagerAndUsers()));
    }

}
