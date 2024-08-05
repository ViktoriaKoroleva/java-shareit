package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item createItem(int userId, Item item);

    Item updateItem(int userId, int itemId, Item itemDto);

    Item findItemById(int itemId);

    List<Item> findUserItemsById(int userId);

    List<Item> findItemByText(int userId, String text);
}
