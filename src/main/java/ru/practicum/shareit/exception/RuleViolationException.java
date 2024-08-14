package ru.practicum.shareit.exception;

public class RuleViolationException extends RuntimeException {

    public RuleViolationException(String message) {
        super(message);
    }
}