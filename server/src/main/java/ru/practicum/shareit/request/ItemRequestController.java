package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestAddDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ResponseEntity<ItemRequestDto> addItemRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                                         @RequestBody ItemRequestAddDto itemRequestAddDto) {
        log.info("Получен POST запрос addItemRequest с объектом: {} с userId: {}", itemRequestAddDto, userId);
        return ResponseEntity.ok().body(itemRequestService.addItemRequest(userId, itemRequestAddDto));
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestResponseDto>> getItemRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Получен GET запрос getItemRequests пользователя с id: {}", userId);
        return ResponseEntity.ok().body(itemRequestService.getItemRequests(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllItemRequests(@RequestHeader("X-Sharer-User-Id") long userId,
                                                                   @RequestParam(defaultValue = "0", required = false) int from,
                                                                   @RequestParam(defaultValue = "20", required = false) int size) {
        log.info("Получен GET запрос getAllItemRequests с параметрами from: {}, size: {}", from, size);
        return ResponseEntity.ok().body(itemRequestService.getAllItemRequests(userId, from, size));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestResponseDto> getItemRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                                                 @PathVariable long requestId) {
        log.info("Получен GET запрос getItemRequest с id: {}", requestId);
        return ResponseEntity.ok().body(itemRequestService.getItemRequest(userId, requestId));
    }
}