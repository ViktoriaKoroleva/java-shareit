package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.util.HttpHeaders;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemResponse createItem(@Valid @RequestBody CreateItemRequest itemDto,
                                   @RequestHeader(HttpHeaders.USER_ID) int userId) {
        return itemService.createItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemResponse updateItem(@RequestHeader(HttpHeaders.USER_ID) int userId,
                                   @PathVariable int itemId,
                                   @Valid @RequestBody UpdateItemRequest itemDto) {
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemResponse findItemById(@RequestHeader(HttpHeaders.USER_ID) int userId,
                                     @PathVariable int itemId) {
        return itemService.findItemById(itemId, userId);
    }

    @GetMapping
    public List<ItemResponse> findUserItemsById(@RequestHeader(HttpHeaders.USER_ID) int userId) {
        return itemService.findUserItemsById(userId);
    }

    @GetMapping("/search")
    public List<ItemResponse> findItemByText(@RequestHeader(HttpHeaders.USER_ID) int userId,
                                             @RequestParam("text") String text) {
        return itemService.findItemByText(userId, text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponse createComment(@RequestHeader(HttpHeaders.USER_ID) int userId,
                                         @PathVariable int itemId, @Valid @RequestBody CreateCommentRequest commentDto) {
        return itemService.createComment(userId, itemId, commentDto);
    }
}
