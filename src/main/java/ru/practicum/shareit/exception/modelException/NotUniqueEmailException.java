package ru.practicum.shareit.exception.modelException;


import java.util.NoSuchElementException;

public class NotUniqueEmailException extends NoSuchElementException {
    public NotUniqueEmailException(String message) {
        super(message);
    }
}
