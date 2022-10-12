package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

@SpringBootTest
class ShareItTests {

//	private final BookingService bookingService;
//
//	ShareItTests(BookingRepository bookingRepository) {
//		this.bookingRepository = bookingRepository;
//	}

	@Test
	void contextLoads() {
	}

	@Test
	void getAllBookingByUserId() {
		Long userId = Long.valueOf(1);
//		List<Booking> bookings = bookingService
	}

}
