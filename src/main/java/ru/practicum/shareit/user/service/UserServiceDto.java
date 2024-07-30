package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.UserDto;

import java.util.List;

public interface UserServiceDto {

    UserDto add(UserDto userDto);

    UserDto update(String id, UserDto userDto);

    UserDto findById(String id);

    void delete(String id);

    List<UserDto> findAll();
}