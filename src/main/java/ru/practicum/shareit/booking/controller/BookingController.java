package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.dto.CreateBookingRequest;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.util.HttpHeaders;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(@Valid @RequestBody CreateBookingRequest bookingDto,
                                         @RequestHeader(HttpHeaders.USER_ID) int userId) {
        return bookingService.createBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponse confirmOrRejectBooking(@PathVariable int bookingId,
                                                  @RequestParam boolean approved,
                                                  @RequestHeader(HttpHeaders.USER_ID) int userId) {
        return bookingService.confirmOrRejectBooking(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse findBookingById(@RequestHeader(HttpHeaders.USER_ID) int userId,
                                           @PathVariable int bookingId) {
        return bookingService.findBookingById(userId, bookingId);
    }

    @GetMapping()
    public List<BookingResponse> findUserReservationItems(@RequestHeader(HttpHeaders.USER_ID) int userId,
                                                          @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.findUserReservationItems(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingResponse> findOwnerReservationItems(@RequestHeader(HttpHeaders.USER_ID) int userId,
                                                           @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.findOwnerReservationItems(userId, state);
    }

}
