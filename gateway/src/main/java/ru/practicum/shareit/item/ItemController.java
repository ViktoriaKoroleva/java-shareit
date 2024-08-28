package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentAddDto;
import ru.practicum.shareit.item.dto.ItemAddDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.support.Constants;


@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader(Constants.USER_HEADER) long userId,
                                          @Valid @RequestBody ItemAddDto itemDto) {
        log.info("Adding item: {}", itemDto);
        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(Constants.USER_HEADER) long userId,
                                             @Valid @RequestBody ItemUpdateDto patchDto,
                                             @PathVariable int itemId) {
        log.info("Updating by id: {} item: {}", itemId, patchDto);
        return itemClient.updateItem(userId, itemId, patchDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItems(@RequestHeader(Constants.USER_HEADER) long userId) {
        log.info("Getting all items by user: {}", userId);
        return itemClient.getAllItems(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@RequestHeader(Constants.USER_HEADER) long userId,
                                              @PathVariable long itemId) {
        return itemClient.getItemById(itemId, userId);

    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestParam String text) {
        log.info("Searching by text: {}", text);
        return itemClient.getItemsByText(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addCommentToItem(@RequestHeader(Constants.USER_HEADER) long userId,
                                                   @PathVariable long itemId,
                                                   @Valid @RequestBody CommentAddDto commentAddDto) {
        log.info("Adding comment to item: {}", itemId);
        return itemClient.postCommentOnItem(userId, itemId, commentAddDto);
    }
}
