package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;

import java.util.List;

public interface BookingService {
    Booking add(Long userId, BookingDto bookingDto);

    Booking approve(Long userId, Long bookingId, Boolean approved);

//    Item update(Long userId, Long id, ItemDto itemDto);
//
//    void delete(Long id, Long userId);
//
    Booking find(Long userId, Long id);

//    Booking findLast(Long itemId);
//
//    Booking findNext(Long itemId);
//
    List<Booking> findAll(Long userId, String state);

    List<Booking> findAllByOwner(Long ownerId, String state);
//
//    List<Item> findItemsByText(String text);

}
