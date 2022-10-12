package ru.practicum.shareit.errors;

public class UnsupportedStatus extends RuntimeException{
    public UnsupportedStatus(String message) {
        super(message);
    }
}
