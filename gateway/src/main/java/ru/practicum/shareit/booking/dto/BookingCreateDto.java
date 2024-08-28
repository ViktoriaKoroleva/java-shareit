package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingCreateDto {
    @NotNull(message = "itemId not null")
    Long itemId;
    @FutureOrPresent(message = "start in the past")
    @NotNull(message = "start not null")
    LocalDateTime start;
    @Future(message = "end in the past")
    @NotNull(message = "end not null")
    LocalDateTime end;

    @AssertTrue(message = "end before start")
    public boolean isEndAfterStart() {
        if (end != null && start != null) {
            return end.isAfter(start);
        } else {
            return true;
        }
    }

    @AssertTrue(message = "start equals end")
    public boolean isStartEqualEnd() {
        if (end != null && start != null) {
            return !start.isEqual(end);
        } else {
            return true;
        }
    }
}

