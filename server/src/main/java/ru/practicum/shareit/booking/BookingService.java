package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {
    Booking add(Long userId, BookingDto bookingDto);

    Booking approve(Long userId, Long bookingId, Boolean approved);

    Booking find(Long userId, Long id);

    List<Booking> findAll(Long userId, String state);

    List<Booking> findAll(Long userId, String state, Long from, Long size);

    List<Booking> findAllByOwner(Long ownerId, String state, Long from, Long size);

    List<Booking> findAllByOwner(Long ownerId, String state);

}
