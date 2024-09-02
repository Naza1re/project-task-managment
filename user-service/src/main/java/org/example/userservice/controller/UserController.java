package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.request.UserRequest;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.dto.response.UserResponseList;
import org.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @AuthenticationPrincipal OAuth2User user,
            @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(user,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<UserResponseList> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String id, @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.updateUserById(id, request));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(
            @PathVariable String id
    ) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(userService.deleteUserById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{userId}/add/project/{projectId}")
    public ResponseEntity<UserResponse> addProjectToUser(
            @PathVariable String projectId,
            @PathVariable String userId) {
        return ResponseEntity.ok(userService.addProjectToUser(userId,projectId));
    }

}
