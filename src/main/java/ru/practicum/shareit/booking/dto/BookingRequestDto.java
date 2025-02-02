package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@lombok.Data
@lombok.AllArgsConstructor
public class BookingRequestDto {
    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

    @NotNull
    private Long itemId;
}
