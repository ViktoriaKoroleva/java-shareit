package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item create(Item item);

    Item update(Item item);

    Item findById(int itemId);

    List<Item> findAllByUserId(int userId);

    List<Item> findByText(String text);
}
