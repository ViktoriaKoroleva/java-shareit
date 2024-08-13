package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.dto.CreateBookingRequest;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(CreateBookingRequest bookingDto, int userId);

    BookingResponse confirmOrRejectBooking(int bookingId, boolean approved, int userId);

    BookingResponse findBookingById(int userId, int bookingId);

   List<BookingResponse> findUserReservationItems(int userId, String state);

   List<BookingResponse> findOwnerReservationItems(int userId, String state);
}
