package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.exception.validDate.StartBeforeEndDateValid;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@StartBeforeEndDateValid
public class CreateBookingRequest {
    private LocalDateTime start;
    private LocalDateTime end;
    @NotNull
    private Integer itemId;
}
