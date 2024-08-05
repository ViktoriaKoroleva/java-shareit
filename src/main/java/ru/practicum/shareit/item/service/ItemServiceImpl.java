package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ModelMapper mapper;
    private final ItemRepositoryImpl repository;

    @Override
    public Item createItem(int userId, CreateItemRequest itemDto) {
        log.info("user '{}' create item", userId);
        Item item = mapper.map(itemDto, Item.class);
        return repository.createItem(userId, item);
    }

    @Override
    public Item updateItem(int userId, int itemId, UpdateItemRequest itemDto) {
        log.info("update item by id '{}' user id '{}'", itemId, userId);
        Item item = mapper.map(itemDto, Item.class);
        return repository.updateItem(userId, itemId, item);
    }

    @Override
    public Item findItemById(int itemId) {
        log.info("find item by id '{}'", itemId);
        return repository.findItemById(itemId);
    }

    @Override
    public List<Item> findUserItemsById(int userId) {
        log.info("find user items by id '{}'", userId);
        return repository.findUserItemsById(userId);
    }

    @Override
    public List<Item> findItemByText(int userId, String text) {
        log.info("find item by text {}", text);
        return repository.findItemByText(userId, text);
    }
}
