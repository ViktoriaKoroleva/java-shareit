package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestAddDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto addItemRequest(long userId, ItemRequestAddDto itemRequestAddDto);

    List<ItemRequestResponseDto> getItemRequests(long userId);

    List<ItemRequestDto> getAllItemRequests(long userId, int from, int size);

    ItemRequestResponseDto getItemRequest(long userId, long requestId);
}
