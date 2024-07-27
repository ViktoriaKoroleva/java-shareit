package ru.practicum.shareit.booking;

import lombok.*;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class Booking {
    Long id;
    Long itemId;
    Long userId;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
