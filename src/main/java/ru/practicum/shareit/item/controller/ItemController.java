package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item createItem(@Valid @RequestBody CreateItemRequest itemDto,
                           @RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.createItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") int userId,
                           @PathVariable int itemId,
                           @RequestBody UpdateItemRequest itemDto) {
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public Item findItemById(@PathVariable int itemId) {
        return itemService.findItemById(itemId);
    }

    @GetMapping
    public List<Item> findUserItemsById(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.findUserItemsById(userId);
    }

    @GetMapping("/search")
    public List<Item> findItemByText(@RequestHeader("X-Sharer-User-Id") int userId,
                                     @RequestParam("text") String text) {
        return itemService.findItemByText(userId, text);
    }
}
