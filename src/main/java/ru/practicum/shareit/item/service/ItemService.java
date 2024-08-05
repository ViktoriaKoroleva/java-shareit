package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item createItem(int userId, CreateItemRequest itemDto);

    Item updateItem(int userId, int itemId, UpdateItemRequest itemDto);

    Item findItemById(int itemId);

    List<Item> findUserItemsById(int userId);

    List<Item> findItemByText(int userId, String text);
}
