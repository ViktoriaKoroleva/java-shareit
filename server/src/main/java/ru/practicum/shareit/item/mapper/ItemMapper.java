package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfoDto;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    Item toModel(ItemDto itemDto);

    ItemDto toDto(Item item);

    ItemInfoDto toItemInfoDto(Item item);

}