package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exception.NotFoundRecordInBD;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();


    User addToStorage(User user) throws ValidateException, NotFoundRecordInBD;

    User updateInStorage(User user);

    void removeFromStorage(Long id);

    void addEachOtherAsFriends(Long id1, Long id2);

    void deleteFromFriends(Long id1, Long id2);

    List<User> getCommonFriends(Long id1, Long id2);

    List<User> getUserFriends(Long id);

    Integer idFromDBByID(Long id);

    Long getUserIdByEmail(String email);

}
