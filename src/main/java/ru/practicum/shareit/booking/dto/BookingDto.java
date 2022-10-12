package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class BookingDto {

    private Long itemId;

    private Long bookerId;

    private LocalDateTime start;

    private LocalDateTime end;

}
