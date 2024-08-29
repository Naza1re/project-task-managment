package org.example.userservice.controller;

import jakarta.ws.rs.GET;
import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.dto.UserResponseList;
import org.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<UserResponseList> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String id, @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.updateUserById(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(
            @PathVariable String id
    ) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(userService.deleteUserById(id));
    }
}
