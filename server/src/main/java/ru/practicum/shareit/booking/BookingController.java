package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public Booking add(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody BookingDto bookingDto) {
        return bookingService.add(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public Booking updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @PathVariable Long bookingId,
                              @RequestParam boolean approved) {
        return bookingService.approve(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public Booking getBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        return bookingService.find(userId, bookingId);
    }

    @GetMapping("")
    public List<Booking> getAllPag(@RequestHeader("X-Sharer-User-Id") Long userId,
                                   @RequestParam(required = false) String state,
                                   @RequestParam(required = false) Long from,
                                   @RequestParam(required = false) Long size) {
        return bookingService.findAll(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<Booking> getAllByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @RequestParam(required = false) String state,
                                       @RequestParam(required = false) Long from,
                                       @RequestParam(required = false) Long size) {
        return bookingService.findAllByOwner(userId, state, from, size);
    }

}
