package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    User create(User user);

    User update(User user);

    User getById(int id);

    List<User> findAll();

    void delUserById(int id);
}
