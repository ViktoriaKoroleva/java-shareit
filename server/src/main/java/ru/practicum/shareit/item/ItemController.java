package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfoDto;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Collection<ItemDto>> getAllByOwner(@RequestHeader("X-Sharer-User-Id") String ownerId) {
        log.info("Вызов метода GET всех инструментов для пользователя с id={}", ownerId);
        return ResponseEntity.ok().body(itemService.getAllByOwner(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemInfoDto> getById(@RequestHeader("X-Sharer-User-Id") String ownerId, @PathVariable long id) {
        log.info("Вызов метода GET инструмента с id={} для пользователя с id={}", id, ownerId);
        return ResponseEntity.ok().body(itemService.getItem(ownerId, id));
    }

    @PostMapping
    public ResponseEntity<ItemDto> add(@RequestHeader("X-Sharer-User-Id") String ownerId,
                                       @RequestBody ItemDto itemDto) {
        log.info("Вызов метода POST инструмента: ownerId={}, item={}", ownerId, itemDto);
        return ResponseEntity.ok().body(itemService.saveItem(ownerId, itemDto));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> update(@RequestHeader("X-Sharer-User-Id") String ownerId,
                                          @RequestBody ItemDto itemDto,
                                          @PathVariable(required = false) Long itemId) {
        log.info("Вызов метода PATCH инструмента {}", itemDto.getName());
        if (itemId != null) {
            itemDto.setId(itemId);
        }
        ItemDto updatedItem = itemService.updateItem(ownerId, itemDto);
        log.info("Инструмент {} с id={} успешно обновлен.", itemDto.getName(), itemDto.getId());
        return ResponseEntity.ok().body(updatedItem);
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<ItemDto>> getSelection(@RequestParam(defaultValue = "") String text) {
        log.info("Вызов метода GET /search инструмента со следующим текстом='{}'", text);
        List<ItemDto> returnedItems = itemService.getSelection(text);
        log.info("Получен список размером {}", returnedItems.size());
        return ResponseEntity.ok().body(returnedItems);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> addComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @PathVariable Long itemId,
                                 @RequestBody CommentDto commentDto) {
        log.info("Вызов метода POST /{itemId}/comment инструмента со следующим userId='{}' itemId='{}' commentDto='{}', ", userId, itemId, commentDto);
        return ResponseEntity.ok().body(itemService.addComment(userId, itemId, commentDto));
    }

}