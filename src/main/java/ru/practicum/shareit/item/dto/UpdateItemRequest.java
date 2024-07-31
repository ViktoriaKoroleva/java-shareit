package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemRequest {
    @Positive(message = "ID должен быть положительным числом")
    private int id;

    @Positive(message = "ID пользователя должен быть положительным числом")
    private  int idOwner;

    @NotEmpty(message = "name is not be empty")
    private  String name;

    @NotEmpty(message = "description is not be empty")
    private String description;

    @NotNull(message = "available is not be null")
    private Boolean available;
}
