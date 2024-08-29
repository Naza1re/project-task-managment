package org.example.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.dto.UserResponseList;
import org.example.userservice.exception.UserAlreadyExistByPhoneException;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.service.UserService;
import org.example.userservice.utill.ExceptionMessages;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserResponse createUser(UserRequest request) {
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

    private void checkUserAlreadyExist(UserRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new UserAlreadyExistByPhoneException(String.format(ExceptionMessages.USER_ALREADY_EXIST_BY_PHONE, request.getPhone()));
        }
    }

    private User getOrThrow(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, id)));
    }
}
