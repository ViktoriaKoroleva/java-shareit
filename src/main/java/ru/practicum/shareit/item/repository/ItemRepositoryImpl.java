package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.modelException.UserNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final UserRepository userRepository;
    private final Map<Integer, Item> items = new HashMap<>();
    private final Map<Integer, List<Item>> userItemIndex = new LinkedHashMap<>();

    private int nextId = 1;

    @Override
    public Item createItem(int userId, Item item) {
        User user = userRepository.getById(userId);

        if (user == null) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        item.setIdOwner(userId);
        item.setId(generateId());
        int id = item.getId();
        items.put(id, item);

        final List<Item> userItems = userItemIndex.computeIfAbsent(userId, k -> new ArrayList<>());
        userItems.add(item);

        return findItemById(id);
    }

    @Override
    public Item updateItem(int userId, int itemId, Item itemDto) {
        User user = userRepository.getById(userId);
        Item item = items.get(itemId);

        if (user.getId() != item.getIdOwner()) {
            throw new UserNotFoundException("user does not match the item owner");
        }

        if (itemDto.getName() != null && !itemDto.getName().isBlank()) {
            item.setName(itemDto.getName());
        }

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) {
            item.setDescription(itemDto.getDescription());
        }

        if (itemDto.getRequest() != null) {
            item.setRequest(itemDto.getRequest());
        }

        return item;
    }

    @Override
    public Item findItemById(int itemId) {
        return items.get(itemId);
    }

    @Override
    public List<Item> findUserItemsById(int userId) {
        return userItemIndex.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public List<Item> findItemByText(int userId, String text) {
        String textLowerCase = text.toLowerCase();
        return items.values().stream()
                .filter(item -> item.getAvailable().equals(true))
                .filter(item -> item.getName().toLowerCase().contains(textLowerCase)
                        || item.getDescription().toLowerCase().contains(textLowerCase))
                .collect(Collectors.toList());
    }

    private int generateId() {
        return nextId++;
    }

}
