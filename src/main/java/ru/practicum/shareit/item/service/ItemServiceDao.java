package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemServiceDao {

    Item add(Item item);

    Item update(Item item);

    Optional<Item> findItemById(String itemId);

    List<Item> findAll(String userId);

    List<Item> search(String text);
}