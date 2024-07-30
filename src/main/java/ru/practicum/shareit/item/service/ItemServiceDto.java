package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemServiceDto {

    ItemDto add(String userId, ItemDto itemDto);

    ItemDto update(String userId, String itemId, ItemDto itemDto);

    ItemDto findItemById(String userId, String itemId);

    List<ItemDto> findAll(String userId);

    List<ItemDto> search(String userId, String text);
}