package ru.practicum.shareit.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String stackTrace;
    private String error;

    public ErrorResponse(String message) {
        this.message = message;
    }
}