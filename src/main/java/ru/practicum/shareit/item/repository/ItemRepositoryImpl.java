package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Integer, Item> items = new HashMap<>();
    private final Map<Integer, List<Item>> userItemIndex = new LinkedHashMap<>();
    private int nextId = 1;

    @Override
    public Item create(Item item) {
        item.setId(generateId());
        items.put(item.getId(), item);
        userItemIndex.computeIfAbsent(item.getIdOwner(), k -> new ArrayList<>()).add(item);
        return item;
    }

    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item findById(int itemId) {
        return items.get(itemId);
    }

    @Override
    public List<Item> findAllByUserId(int userId) {
        return userItemIndex.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public List<Item> findByText(String text) {
        String textLowerCase = text.toLowerCase();
        return items.values().stream()
                .filter(item -> item.getAvailable())
                .filter(item -> item.getName().toLowerCase().contains(textLowerCase)
                        || item.getDescription().toLowerCase().contains(textLowerCase))
                .collect(Collectors.toList());
    }

    private int generateId() {
        return nextId++;
    }
}
