package ru.practicum.shareit.item.repository;


import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    Item add(Item item);

    List<Item> getAllItems(Long userId);

    Item getItemById(Long id);

    Boolean isExcludeItemById(Long id);

    void removeItemById(Long id);

    void removeItemsByUserId(Long userId);

    public Item updateInStorage(Item item, boolean[] isUpdateField);

    List<Item> searchItemsByText(String text);
}
