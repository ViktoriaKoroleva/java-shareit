package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId() != null ? user.getId().toString() : null,
                user.getName(),
                user.getEmail()
        );
    }

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId() != null ? userDto.getId() : null,
                userDto.getName(),
                userDto.getEmail()
        );
    }
}