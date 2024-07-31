package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;



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
