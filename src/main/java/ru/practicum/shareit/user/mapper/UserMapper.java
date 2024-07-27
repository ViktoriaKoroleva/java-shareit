package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToModel(UserDto itemDto);

    UserDto mapToDto(User user);
}
