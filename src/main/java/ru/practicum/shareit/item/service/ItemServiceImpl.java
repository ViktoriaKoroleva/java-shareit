package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.modelException.NotUniqueEmailException;
import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ModelMapper mapper;
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public Item createItem(int userId, CreateItemRequest itemDto) {
        log.info("User '{}' creates item", userId);
        User user = userService.getById(userId);
        Item item = mapper.map(itemDto, Item.class);
        item.setIdOwner(user.getId());
        return itemRepository.create(item);
    }

    @Override
    public Item updateItem(int userId, int itemId, UpdateItemRequest itemDto) {
        log.info("User '{}' updates item with id '{}'", userId, itemId);
        Item item = itemRepository.findById(itemId);
        if (item.getIdOwner() != userId) {
            throw new NotUniqueEmailException("User does not match the item owner");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }

        return itemRepository.update(item);
    }

    @Override
    public Item findItemById(int itemId) {
        log.info("Finding item by id '{}'", itemId);
        return itemRepository.findById(itemId);
    }

    @Override
    public List<Item> findUserItemsById(int userId) {
        log.info("Finding items for user with id '{}'", userId);
        return itemRepository.findAllByUserId(userId);
    }

    @Override
    public List<Item> findItemByText(int userId, String text) {
        log.info("Finding items by text '{}'", text);
        return itemRepository.findByText(text);
    }
}