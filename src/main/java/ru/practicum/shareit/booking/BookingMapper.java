package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;


public interface BookingMapper {
    BookingDto bookingToBookingDto(Booking booking);

    Booking bookingDtoToBooking(Long userId, BookingDto bookingDto);
}
