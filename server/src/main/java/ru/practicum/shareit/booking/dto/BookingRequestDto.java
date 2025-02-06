package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

@lombok.Data
@lombok.AllArgsConstructor
public class BookingRequestDto {
    private LocalDateTime start;

    private LocalDateTime end;

    private Long itemId;
}
