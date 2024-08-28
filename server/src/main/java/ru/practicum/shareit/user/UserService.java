package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto user);

    UserDto updateUser(long id, UserDto user);

    UserDto getUser(long id);

    List<UserDto> getAllUsers();

    void deleteUser(long id);
}