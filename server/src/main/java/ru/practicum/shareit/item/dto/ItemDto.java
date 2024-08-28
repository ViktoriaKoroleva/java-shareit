package ru.practicum.shareit.item.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;

}