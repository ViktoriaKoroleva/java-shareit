package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {
    @NotEmpty(message = "name is not be empty")
    private String name;

    @NotEmpty(message = "description is not be empty")
    private String description;

    @NotNull(message = "available is not be null")
    private Boolean available;
}
