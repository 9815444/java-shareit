package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByStartDesc(Long userId);

    Page<Booking> findByUserIdOrderByStartDesc(Long userId, Pageable pageable);

    List<Booking> findByUserIdAndItemIdAndStatusAndEndBefore(Long userId, Long itemId, String status, LocalDateTime currentDate);

    List<Booking> findByItemIdInOrderByStartDesc(List<Long> itemsId);

    Page<Booking> findByItemIdInOrderByStartDesc(List<Long> itemsId, Pageable pageable);

    List<Booking> findByItemIdAndStartAfterOrderByStart(Long itemId, LocalDateTime date);

    List<Booking> findByItemIdAndEndBeforeOrderByEndDesc(Long itemId, LocalDateTime date);

}