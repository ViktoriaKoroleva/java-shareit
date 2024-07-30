package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.service.UserServiceDaoImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceDtoImpl implements ItemServiceDto {
    private final ItemServiceDao itemDao;
    private final UserServiceDaoImpl userService;

    @Override
    public ItemDto add(String userId, ItemDto itemDto) { // Изменено с Long на String
        UserDto user = UserMapper.toUserDto(userService.findById(userId));
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(UserMapper.toUser(user).getId());
        return ItemMapper.toItemDto(itemDao.add(item));
    }

    @Override
    public ItemDto update(String userId, String itemId, ItemDto itemDto) { // Изменено с Long на String
        Optional<Item> itemOptional = itemDao.findItemById(itemId);
        if (itemOptional.isPresent()) {
            if (!itemOptional.get().getOwner().equals(userId)) {
                throw new NotFoundException(String.format("Пользователь с id %s " +
                        "не является владельцем вещи id %s.", userId, itemId));
            }
            Item itemFromStorage = itemOptional.get();
            Item item = ItemMapper.toItem(itemDto);
            if (Objects.isNull(item.getAvailable())) {
                item.setAvailable(itemFromStorage.getAvailable());
            }
            if (Objects.isNull(item.getDescription())) {
                item.setDescription(itemFromStorage.getDescription());
            }
            if (Objects.isNull(item.getName())) {
                item.setName(itemFromStorage.getName());
            }
            item.setId(itemFromStorage.getId());
            item.setRequest(itemFromStorage.getRequest());
            item.setOwner(itemFromStorage.getOwner());

            return ItemMapper.toItemDto(itemDao.update(item));
        }
        return itemDto;
    }

    @Override
    public ItemDto findItemById(String userId, String itemId) { // Изменено с Long на String
        userService.findById(userId);
        Optional<Item> itemGet = itemDao.findItemById(itemId);
        if (itemGet.isEmpty()) {
            throw new NotFoundException(String.format("У пользоватея с id %s не " +
                    "существует вещи с id %s", userId, itemId));
        }
        return ItemMapper.toItemDto(itemGet.get());
    }

    @Override
    public List<ItemDto> findAll(String userId) { // Изменено с Long на String
        userService.findById(userId);
        List<Item> itemList = itemDao.findAll(userId);
        return itemList.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String userId, String text) { // Изменено с Long на String
        userService.findById(userId);
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        List<Item> itemList = itemDao.search(text);
        return itemList.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}