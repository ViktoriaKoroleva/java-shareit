package ru.practicum.shareit.exception;

public class BookingServiceException extends RuntimeException {
    public BookingServiceException(String message) {
        super(message);
    }
}
