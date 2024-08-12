package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {
    ItemResponse createItem(int userId, CreateItemRequest itemDto);

    ItemResponse updateItem(int userId, int itemId, UpdateItemRequest itemDto);

    ItemResponse findItemById(int itemId, int userId);

    List<ItemResponse> findUserItemsById(int userId);

    List<ItemResponse> findItemByText(int userId, String text);

    CommentResponse createComment(int userId, int itemId, CreateCommentRequest commentDto);
}

