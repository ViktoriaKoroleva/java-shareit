package ru.practicum.shareit.Validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundRecordInBD;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@Slf4j
public class ValidationService {
    ItemRepository itemRepository;
    UserRepository userRepository;

    @Autowired
    public ValidationService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public void checkUniqueEmailToUpdate(User user) {
        final Long inputId = user.getId();
        final String inputEmail = user.getEmail();
        final String inputName = user.getName();

        if (inputId == null) {
            String message = "1. Обновление пользователя невозможно, поскольку ID входящего пользователя " +
                    "должен быть не Null.";
            log.info(message + " Входящий пользователь: " + user);
            throw new NotFoundRecordInBD(message);
        }

        final Long idFromDbByEmail = userRepository.getUserIdByEmail(inputEmail);
        if (idFromDbByEmail != null && !inputId.equals(idFromDbByEmail)) {
            String message = String.format("2. Обновление пользователя невозможно, поскольку email = {} " +
                    "принадлежит пользователю с ID = {}.", inputEmail, idFromDbByEmail);
            log.info(message + " Входящий пользователь: " + user);
            throw new ConflictException(message);
        }
    }


    public void checkUniqueEmailToCreate(User user) {
        final Long inputId = user.getId();
        final String inputEmail = user.getEmail();


        Long idFromDbByEmail = userRepository.getUserIdByEmail(inputEmail);
        if (idFromDbByEmail != null) {
            String message = String.format("Создание пользователя невозможно, поскольку email = {} входящего " +
                    "принадлежит пользователю: {}.", inputEmail, userRepository.getUserById(idFromDbByEmail));
            log.info(message);
            throw new ConflictException(message);
        }
    }

    public void checkUniqueEmail(User user) throws ConflictException {
        final String newEmail = user.getEmail();
        final Long idFromDB = userRepository.getUserIdByEmail(newEmail);

        if (idFromDB.equals(user.getId())) {
            String message = "";
            throw new NotFoundRecordInBD(message);
        }


        if (idFromDB != null && user.getId() != null && !idFromDB.equals(user.getId())) {
            String message = String.format("Email = '{}' уже есть в БД.", newEmail);
            log.info(message + "Email принадлежит пользователю: {}." + userRepository.getUserById(idFromDB) + ".");
            throw new ConflictException(message);
        }
    }

    public void validateUserFields(User user) {
        final String email = user.getEmail();
        if (email == null || email.isBlank()) {
            String error = "Email пользователя не может пустым.";
            log.info(error);
            throw new ValidateException(error);
        }

        final String name = user.getName();
        if (name == null || name.isBlank()) {
            String error = "Имя пользователя не может быть пустым.";
            log.info(error);
            throw new ValidateException(error);
        }
    }

    public boolean[] checkFieldsForUpdate(User user) {
        boolean[] result;
        result = new boolean[2];
        final String name = user.getName();
        final String email = user.getEmail();
        result[0] = (name != null) && !name.isBlank();
        result[1] = (email != null) && !email.isBlank();

        if (result[0] || result[1]) {
            return result;
        }
        throw new ValidateException("Все поля пользователя равны 'null'.");
    }

    public User checkExistUserInDB(Long userId) {
        User result = userRepository.getUserById(userId);
        if (result == null) {
            String error = String.format("Error 404. Пользователь с ID = '%d' не найден в БД.", userId);
            log.info(error);
            throw new NotFoundRecordInBD(error);
        }
        return result;

    }

    public Item checkExistItemInDB(Long itemId) {
        Item item = itemRepository.getItemById(itemId);
        if (item == null) {
            String message = String.format("Вещь с ID = '%d' не найдена в БД.", itemId);
            log.info("Error 404. " + message);
            throw new NotFoundRecordInBD(message);
        }
        return item;
    }

    public void checkMissingItemInDB(Long itemId) {
        Item item = itemRepository.getItemById(itemId);
        if (item != null) {
            String message = String.format("Вещь с ID = '%d' найдена в БД. %s", itemId, item);
            log.info("Error 409. " + message);
            throw new ConflictException(message);
        }
    }

    public void validateItemFields(Item item) {
        final String name = item.getName();
        if (name == null || name.isBlank()) {
            String error = "Название вещи не может пустым.";
            log.info(error);
            throw new ValidateException(error);
        }

        final String description = item.getDescription();
        if (description == null || description.isBlank()) {
            String error = "Описание вещи не может быть пустым.";
            log.info(error);
            throw new ValidateException(error);
        }
        final Boolean available = item.getAvailable();
        if (available == null) {
            String error = "Для вещи необходим статус её бронирования.";
            log.info(error);
            throw new ValidateException(error);
        }
        final Long ownerId = item.getOwnerId();
        if (ownerId == null) {
            String error = "Для вещи необходим хозяин.";
            log.info(error);
            throw new ValidateException(error);
        }
    }

    public boolean[] checkFieldsForUpdate(Item item) {
        boolean[] result;
        result = new boolean[3];
        result[0] = (item.getName() != null) && !item.getName().isBlank();
        result[1] = (item.getDescription() != null) && !item.getDescription().isBlank();
        result[2] = item.getAvailable() != null;

        for (boolean b : result) {
            return result;
        }
        throw new ValidateException("Все поля: название, описание и статус доступа к аренде равны 'null'.");
    }

    public boolean isOwnerItem(Item item, Long ownerId) {
        if (item == null || ownerId == null) {
            String message = "Вещь и (или) ID хозяина вещи равны null.";
            log.info("Error 400. {}", message);
            throw new ValidateException(message);
        }
        if (!ownerId.equals(item.getOwnerId())) {
            String message = String.format("Вещь {} не принадлежит хозяину с ID = {}.", item.getName(), ownerId);
            log.info("Error 404. {}", message);
            throw new NotFoundRecordInBD(message);
        }
        return (ownerId.equals(item.getOwnerId()));
    }
}

