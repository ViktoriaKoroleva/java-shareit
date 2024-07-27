package ru.practicum.shareit.item.dto;

import lombok.Data;

import java.util.Set;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    Long id;
    String name;
    String description;
    Long ownerId;
    Boolean available;
    Set<Long> reviews;
}
