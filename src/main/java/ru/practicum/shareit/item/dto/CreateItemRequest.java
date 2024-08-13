package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {
    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "available is not be null")
    private Boolean available;
}
