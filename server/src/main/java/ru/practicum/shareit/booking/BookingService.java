package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import java.util.List;

public interface BookingService {
    BookingDto saveBooking(Long userId, BookingRequestDto bookingRequestDto);

    BookingDto setApproved(Long userId, Long bookingId, Boolean approved);

    BookingDto getBooking(Long bookingId, Long userId);

    List<BookingDto> getAllByBookerAndStatus(Long userId, String state);

    List<BookingDto> getAllByOwnerAndStatus(Long userId, String state);
}
