package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

@Component
@Mapper
public class BookingMapperImpl implements BookingMapper {

    private final ItemService itemService;
    private final UserService userService;

    public BookingMapperImpl(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @Override
    public BookingDto bookingToBookingDto(Booking booking) {
        return new BookingDto(booking.getItemId(), booking.getStart(), booking.getEnd());
    }

    @Override
    public Booking bookingDtoToBooking(Long userId, BookingDto bookingDto) {
        Item item = itemService.find(bookingDto.getItemId());
        User user = userService.findUser(userId);
        return new Booking(null, null, bookingDto.getItemId(),
                item, user, bookingDto.getStart(), bookingDto.getEnd(), null);
    }
}
