package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.Validation.ValidationService;
import ru.practicum.shareit.exception.NotFoundRecordInBD;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationService validationService;

    public UserServiceImpl(@Qualifier("InMemory") UserRepository userRepository, ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }

    /**
     * Получить пользователя по ID.
     * @param id ID пользователя.
     * @return User - пользователь присутствует в библиотеке.
     * <p>null - пользователя нет в библиотеке.</p>
     */
    @Override
    public User getUserById(Long id) {
        User result = userRepository.getUserById(id);
        if (result == null) {
            String error = "В БД отсутствует запись о пользователе при получении пользователя по ID = " + id + ".";
            log.info(error);
            throw new NotFoundRecordInBD(error);
        }
        return result;
    }

    /**
     * Получение списка всех пользователей.
     * @return Список пользователей.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsersFromStorage();
    }

    /**
     * Добавить юзера в БД.
     * @param user пользователь.
     * @return добавляемый пользователь.
     */
    @Override
    public User addToStorage(User user) throws ValidateException, NotFoundRecordInBD {
        validationService.validateUserFields(user);
        validationService.checkUniqueEmailToCreate(user);
        return userRepository.addToStorage(user);
    }

    @Override
    public User updateInStorage(User user) {
        validationService.checkExistUserInDB(user.getId());
        boolean[] isUpdateFields = validationService.checkFieldsForUpdate(user);
        validationService.checkUniqueEmailToUpdate(user);
        return userRepository.updateInStorage(user, isUpdateFields);
    }

    @Override
    public void removeFromStorage(Long id) {
        userRepository.removeFromStorage(id);
    }

    @Override
    public void addEachOtherAsFriends(Long id1, Long id2) {

    }

    @Override
    public void deleteFromFriends(Long id1, Long id2) {

    }

    @Override
    public List<User> getCommonFriends(Long id1, Long id2) {
        return null;
    }

    /**
     * Вывести список друзей пользователя с ID.
     */
    @Override
    public List<User> getUserFriends(Long id) {
        return null;
    }

    /**
     * Метод проверки наличия пользователя в базе данных по ID.
     */
    @Override
    public Integer idFromDBByID(Long id) {
        return null;
    }

    /**
     * Проверка наличия пользователя по `Email`.
     */
    @Override
    public Long getUserIdByEmail(String newEmail) {
        return userRepository.getUserIdByEmail(newEmail);
    }


}

