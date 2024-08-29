package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.NewItemRequestAddDto;
import ru.practicum.shareit.support.Constants;


@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addItemRequest(@RequestHeader(Constants.USER_HEADER) long userId,
                                                 @Valid @RequestBody NewItemRequestAddDto newItemRequestAddDto) {
        log.info("Add item request: {}, userId: {}", newItemRequestAddDto, userId);
        return requestClient.addItemRequest(userId, newItemRequestAddDto);
    }

    @GetMapping
    public ResponseEntity<Object> getItemRequests(@RequestHeader(Constants.USER_HEADER) long userId) {
        log.info("Get item requests userId: {}", userId);
        return requestClient.getItemRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemRequests(@RequestHeader(Constants.USER_HEADER) long userId,
                                                     @RequestParam(defaultValue = "0", required = false) int from,
                                                     @RequestParam(defaultValue = "20", required = false) int size) {
        log.info("Get item requests all users from: {}, size: {}", from, size);
        return requestClient.getAllItemRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequest(@RequestHeader(Constants.USER_HEADER) long userId,
                                                 @PathVariable long requestId) {
        log.info("Get item request: {}}", requestId);
        return requestClient.getItemRequest(userId, requestId);
    }
}
