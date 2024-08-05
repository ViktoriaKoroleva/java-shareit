package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public User create(CreateUserRequest userDto) {
        User user = mapper.map(userDto, User.class);
        log.info("create user by email {}", userDto.getEmail());
        return userRepository.create(user);
    }

    @Override
    public User update(int userId, UpdateUserRequest userDto) {
       User user = mapper.map(userDto, User.class);
        user.setId(userId);
        log.info("update user by id {}", userId);
        return userRepository.update(user);
    }

    @Override
    public User getById(int id) {
        log.info("find user by id {}", id);
        return userRepository.getById(id);
    }

    @Override
    public List<User> findAll() {
        log.info("get all users");
        return userRepository.findAll();
    }

    @Override
    public void delUserById(int id) {
        log.info("delete user by id {}", id);
        userRepository.delUserById(id);
    }
}
