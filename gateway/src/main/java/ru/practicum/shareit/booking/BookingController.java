package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.support.Constants;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> addBooking(@RequestHeader(Constants.USER_HEADER) long userId,
                                             @Valid @RequestBody BookingCreateDto bookingDto) {
        log.info("Adding new booking: {}", bookingDto);
        return bookingClient.addBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@RequestHeader(Constants.USER_HEADER) long userId,
                                                 @PathVariable long bookingId,
                                                 @RequestParam boolean approved) {
        log.info("Approving booking: {} by owner {}", bookingId, userId);
        return bookingClient.bookingApproved(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader(Constants.USER_HEADER) long userId,
                                             @PathVariable long bookingId) {
        log.info("Getting booking {} by requester or owner: {}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getBookings(@RequestHeader(Constants.USER_HEADER) long userId,
                                              @RequestParam(required = false, defaultValue = "ALL")
                                              String state) {
        log.info("Getting bookings: {} of user {}", state, userId);
        BookingState stateParam = BookingState.from(state)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + state));
        return bookingClient.getBookings(userId, stateParam);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsOwner(@RequestHeader(Constants.USER_HEADER) long userId,
                                                   @RequestParam(required = false, defaultValue = "ALL")
                                                   String state) {
        log.info("Getting bookings: {} of owner {}", state, userId);
        BookingState stateParam = BookingState.from(state)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + state));
        return bookingClient.getBookingsOwner(userId, stateParam);
    }
}
