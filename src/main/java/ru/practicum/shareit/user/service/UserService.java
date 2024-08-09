package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    User create(CreateUserRequest userDto);

    User update(int userId, UpdateUserRequest userDto);

    User getById(int id);

    List<User> findAll();

    void delUserById(int id);

}
