package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemServiceDtoImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    public static final String USER_HEADER = "X-Sharer-User-Id";
    private final ItemServiceDtoImpl itemService;

    @PostMapping
    public ItemDto add(@RequestHeader(USER_HEADER) String userId, // Изменено с Long на String
                       @Valid @RequestBody ItemDto itemDto) {
        log.info("POST Запрос на добавление пользователем с id = " + userId + " предмета " + itemDto.toString());
        return itemService.add(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(USER_HEADER) String userId, // Изменено с Long на String
                          @RequestBody ItemDto itemDto,
                          @PathVariable String itemId) { // Изменено с Long на String
        log.info("PATCH Запрос на обновление предмета с id = " + itemId + " пользователем с id = " + userId);
        return itemService.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto findById(@RequestHeader(USER_HEADER) String userId, // Изменено с Long на String
                            @PathVariable("itemId") String itemId) { // Изменено с Long на String
        log.info("GET Запрос на получение предмета с id = " + itemId + " пользователем с id = " + userId);
        return itemService.findItemById(userId, itemId);
    }

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader(USER_HEADER) String userId) { // Изменено с Long на String
        log.info("GET Запрос на получение предметов пользователя с id = " + userId);
        return itemService.findAll(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestHeader(USER_HEADER) String userId, // Изменено с Long на String
                                     @RequestParam(name = "text") String text) {
        log.info("GET Запрос на поиск предметов");
        return itemService.search(userId, text);
    }
}
