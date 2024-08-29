package ru.practicum.shareit.request.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING, uses = ItemMapper.class)
public interface ItemRequestListMapper {

    List<ItemRequest> toListModel(List<ItemRequestDto> itemRequestDtoList);

    List<ItemRequestDto> toListDto(List<ItemRequest> itemRequestList);

    List<ItemRequestResponseDto> toListItemRequestResponseDto(List<ItemRequest> itemRequestList);
}
