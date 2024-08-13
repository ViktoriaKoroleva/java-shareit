package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(CreateUserRequest userDto);

    UserResponse update(int userId, UpdateUserRequest userDto);

    UserResponse getById(int id);

    List<UserResponse> findAll();

    void delUserById(int id);
}

