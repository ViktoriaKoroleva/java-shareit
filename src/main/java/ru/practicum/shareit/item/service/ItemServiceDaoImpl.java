package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class ItemServiceDaoImpl implements ItemServiceDao {

    private final Map<String, List<Item>> items = new HashMap<>();
    private Long generatorId = 1L;

    @Override
    public Item add(Item item) {
        String generatedId = generatorId.toString();
        item.setId(generatedId);
        generatorId++;
        List<Item> listItems = items.getOrDefault(item.getOwner(), new ArrayList<>());
        listItems.add(item);
        items.put(item.getOwner(), listItems);
        return item;
    }

    @Override
    public Item update(Item item) {
        List<Item> userItems = items.get(item.getOwner().toString());
        if (userItems == null) {
            throw new IllegalStateException("User not found");
        }
        userItems = userItems.stream()
                .filter(userItem -> !userItem.getId().equals(item.getId()))
                .collect(Collectors.toList());
        userItems.add(item);
        items.put(item.getOwner().toString(), userItems); // Преобразуем идентификатор владельца в строку
        return item;
    }

    @Override
    public Optional<Item> findItemById(String itemId) {
        return items.values().stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getId().toString().equals(itemId)) // Преобразуем идентификатор предмета в строку для сравнения
                .findFirst();
    }

    @Override
    public List<Item> findAll(String userId) {
        return new ArrayList<>(items.getOrDefault(userId, Collections.emptyList()));
    }

    @Override
    public List<Item> search(String text) {
        String searchText = text.toLowerCase();
        return items.values().stream()
                .flatMap(Collection::stream)
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(searchText)
                        || item.getDescription().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
    }
}