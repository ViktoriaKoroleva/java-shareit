package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.dto.User;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ItemAddDto {
    Long id;
    @NotBlank(message = "name not blank")
    String name;
    @NotBlank(message = "description not blank")
    String description;
    User owner;
    @NotNull(message = "available not null")
    Boolean available;
    Long requestId;
}
