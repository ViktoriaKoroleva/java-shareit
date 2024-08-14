package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.validator.ValidateWhile;

@Builder
@Getter
@Setter
@ToString
public class ItemDto {

    @Null(groups = ValidateWhile.Create.class, message = "При создании вещи id должно быть null.")
    private Long id;

    @NotBlank(groups = ValidateWhile.Create.class, message = "Имя для Item не может быть пустым")
    private String name;
    @NotBlank(groups = ValidateWhile.Create.class, message = "Описание для Item не может быть пустым")
    private String description;

    @NotNull(groups = ValidateWhile.Create.class)
    private Boolean available;

}