package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByStartDesc(Long userId);

    List<Booking> findByUserIdAndItemIdAndStatusAndEndBefore(Long userId, Long itemId, String status, LocalDateTime currentDate);

    List<Booking> findByItemIdInOrderByStartDesc(List<Long> itemsId);

    List<Booking> findByItemIdAndStartAfterOrderByStart(Long itemId, LocalDateTime date);

    List<Booking> findByItemIdAndEndBeforeOrderByEndDesc(Long itemId, LocalDateTime date);

}