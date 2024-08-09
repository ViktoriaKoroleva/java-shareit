package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;

@Data
public class Item {
    private int id;
    private  int idOwner;
    private  String name;
    private String description;
    private Boolean available;
    private ItemRequest request;
}
