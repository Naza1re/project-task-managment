package org.example.userservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.request.UserRequest;
import org.example.userservice.dto.response.UserResponse;
import org.example.userservice.dto.response.UserResponseList;
import org.example.userservice.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User fromRequestToEntity(UserRequest request) {
        return modelMapper.map(request, User.class);
    }

    public UserResponse fromEntityToResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponseList fromEntityListToResponseList(List<User> allUsers) {
        List<UserResponse> userResponseList = allUsers.stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new UserResponseList(userResponseList);
    }
}
