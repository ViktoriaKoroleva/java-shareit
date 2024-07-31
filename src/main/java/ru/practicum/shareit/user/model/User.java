package ru.practicum.shareit.user.model;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private List<Item> userItems = new ArrayList<>();
}
