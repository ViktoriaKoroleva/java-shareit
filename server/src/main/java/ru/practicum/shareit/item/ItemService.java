package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfoDto;

import java.util.List;

public interface ItemService {

    ItemDto saveItem(String ownerId, ItemDto itemDto);

    ItemDto updateItem(String ownerId, ItemDto itemDto);

    ItemInfoDto getItem(String ownerId, long itemId);

    List<ItemDto> getAllByOwner(String ownerId);

    List<ItemDto> getSelection(String searchText);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);

}