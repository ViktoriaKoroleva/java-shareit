package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserServiceDao {

    User add(User user);

    User update(String id, User user);

    User findById(String id);

    void delete(String id);

    List<User> findAll();
}