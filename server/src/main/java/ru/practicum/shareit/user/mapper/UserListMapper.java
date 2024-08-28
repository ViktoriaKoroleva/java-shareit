package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING, uses = UserMapper.class)
public interface UserListMapper {

    List<UserDto> toListDto(List<User> users);

    List<User> toListModel(List<UserDto> usersDto);

}