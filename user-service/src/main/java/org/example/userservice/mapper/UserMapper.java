package org.example.userservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
