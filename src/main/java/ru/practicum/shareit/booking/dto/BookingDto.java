package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.validator.ValidateWhile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {

    @Null(groups = ValidateWhile.Create.class, message = "При создании бронирования id должно быть null.")
    private Long id;

    @NotNull(groups = ValidateWhile.Create.class, message = "Дата бронирования должна быть в будущем")
    private LocalDateTime start;

    @NotNull(groups = ValidateWhile.Create.class, message = "Дата бронирования должна быть в будущем")
    private LocalDateTime end;
    private ItemDto item;
    private UserDto booker;
    private String status;
}
