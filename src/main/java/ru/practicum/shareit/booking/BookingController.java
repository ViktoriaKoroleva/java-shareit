package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.validator.ValidationGroups;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<BookingDto> create(@RequestHeader(USER_ID_HEADER) Long userId,
                                             @RequestBody @Validated(ValidationGroups.Create.class) BookingRequestDto bookingRequestDto) {
        log.info("Вызов метода POST бронирования: userId={}, booking={}", userId, bookingRequestDto);
        return ResponseEntity.ok().body(bookingService.saveBooking(userId, bookingRequestDto));
    }

    @GetMapping
    public ResponseEntity<Collection<BookingDto>> findAll(
            @RequestHeader(USER_ID_HEADER) Long userId,
            @RequestParam(defaultValue = "ALL") String state) {
        log.info("Вызов метода GET всех бронирований для пользователя с id={}", userId);
        return ResponseEntity.ok().body(bookingService.getAllByBookerAndStatus(userId, state));
    }

    @GetMapping("/owner")
    public ResponseEntity<Collection<BookingDto>> findAllByOwnerAndStatus(
            @RequestHeader(USER_ID_HEADER) Long userId,
            @RequestParam(defaultValue = "ALL", required = false) String state) {
        log.info("Вызов метода GET всех бронирований для пользователя с id={} и статусом={}", userId, state);
        return ResponseEntity.ok().body(bookingService.getAllByOwnerAndStatus(userId, state));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDto> setApproved(@RequestHeader(USER_ID_HEADER) Long userId,
                                                  @PathVariable Long bookingId,
                                                  @RequestParam Boolean approved) {
        log.info("Вызов метода PATCH для подтверждения бронирования с id={}", bookingId);
        return ResponseEntity.ok().body(bookingService.setApproved(userId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> findById(@RequestHeader(USER_ID_HEADER) Long userId,
                                               @PathVariable Long bookingId) {
        log.info("Вызов метода GET бронирования с id={}", bookingId);
        return ResponseEntity.ok().body(bookingService.getBooking(bookingId, userId));
    }
}