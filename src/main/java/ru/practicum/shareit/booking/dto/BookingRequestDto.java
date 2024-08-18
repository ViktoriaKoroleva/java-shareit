package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.validator.ValidationGroups;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDto {
    @NotNull(groups = ValidationGroups.Create.class, message = "Отсутствует Id предмета для бронирования")
    private Long itemId;
    @NotNull(groups = ValidationGroups.Create.class, message = "Отсутствует StartTime начала бронирования")
    private LocalDateTime start;
    @NotNull(groups = ValidationGroups.Create.class, message = "Отсутствует EndTime окончания бронирования")
    private LocalDateTime end;
}
