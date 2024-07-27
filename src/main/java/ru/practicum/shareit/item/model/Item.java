package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    private enum Status {free, busy}

    Long id;
    String name;
    String description;
    Long ownerId;
    Boolean available;
    @JsonIgnore
    Boolean isRequest;
    Set<Long> reviews;
}