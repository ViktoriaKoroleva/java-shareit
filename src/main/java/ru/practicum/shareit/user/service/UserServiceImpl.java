package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicatedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserListMapper;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserListMapper userListMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (!userRepository.findByEmail(userDto.getEmail()).isEmpty()) {
            throw new DuplicatedException("Пользователь с таким email существует");
        }
        User createdUser = userRepository.save(userMapper.toModel(userDto));
        return userMapper.toDto(createdUser);
    }

    @Override
    public UserDto updateUser(long userId, UserDto userDto) {
        User retrievedUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));
        User user = userMapper.toModel(userDto);
        user.setId(userId);
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            user.setEmail(retrievedUser.getEmail());
        } else if (!userRepository.findByEmail(user.getEmail()).isEmpty() &&
                !user.getEmail().equals(retrievedUser.getEmail())) {
            throw new DuplicatedException("Пользователь с таким email существует");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(retrievedUser.getName());
        }

        User newUser = userRepository.save(user);
        return userMapper.toDto(newUser);
    }

    @Override
    public UserDto getUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + id + " не найден"));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userListMapper.toListDto(users);

    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + id + " не найден"));
        userRepository.delete(user);
    }
}