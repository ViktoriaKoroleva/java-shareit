package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.modelException.NotUniqueEmailException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final UserRepository userRepository;
    private final Map<Integer, Item> items = new HashMap<>();

    private int nextId = 1;

    private int generateId() {
        return nextId++;
    }

    @Override
    public Item createItem(int userId, Item item) {
        User user = userRepository.getById(userId);

        item.setIdOwner(userId);
        item.setId(generateId());
        int id = item.getId();
        items.put(id, item);
        user.getUserItems().add(item);
        return findItemById(id);
    }

    @Override
    public Item updateItem(int userId, int itemId, Item itemDto) {
        User user = userRepository.getById(userId);
        Item item = items.get(itemId);

        if (user.getId() != item.getIdOwner()) {
            throw new NotUniqueEmailException("user does not match the item owner");
        }

        int id = item.getId();

        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }

        if (itemDto.getRequest() != null) {
            item.setRequest(itemDto.getRequest());
        }

        items.put(id, item);
        return findItemById(id);
    }

    @Override
    public Item findItemById(int itemId) {
        return items.get(itemId);
    }

    @Override
    public List<Item> findUserItemsById(int userId) {
        User user = userRepository.getById(userId);
        return user.getUserItems();
    }

    @Override
    public List<Item> findItemByText(int userId, String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
       String textLowerCase = text.toLowerCase();
       return items.values().stream()
                .filter(item -> item.getAvailable().equals(true))
                .filter(item -> item.getName().toLowerCase().contains(textLowerCase)
                        || item.getDescription().toLowerCase().contains(textLowerCase))
                .collect(Collectors.toList());
    }

}
