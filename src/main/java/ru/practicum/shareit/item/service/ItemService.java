package ru.practicum.shareit.item.service;



import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item add(Item item, Long ownerId);
    List<Item> getAllItems(Long userId);

    Item updateInStorage(Item item, Long ownerId);

    Item getItemById(Long id);

    Boolean isExcludeItemById(Long id);

    Item removeItemById(Long id);

    List<Item> searchItemsByText(String text);
    
}
