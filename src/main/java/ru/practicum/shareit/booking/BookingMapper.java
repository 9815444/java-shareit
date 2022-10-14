package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {

    public static BookingDto bookingToBookingDto(Booking booking) {
        return new BookingDto(booking.getItemId(), booking.getUserId(), booking.getStart(), booking.getEnd());
    }

    public static Booking bookingDtoToBooking(Long userId, BookingDto bookingDto) {
        return new Booking(null, null, bookingDto.getItemId(),
                null, null, bookingDto.getStart(), bookingDto.getEnd(), null);
    }


}
