package ru.practicum.shareit.request.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.request.dto.ItemRequestAddDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;

@Mapper(componentModel = "spring")
public interface ItemRequestMapper {

    ItemRequest toModel(ItemRequestDto itemRequestDto);

    ItemRequest toItemRequest(ItemRequestAddDto itemRequestAddDto);

    ItemRequestDto toDto(ItemRequest itemRequest);

    ItemRequestAddDto toItemRequestAddDto(ItemRequest itemRequest);

    ItemRequestResponseDto toItemRequestResponseDto(ItemRequest itemRequest);

}
