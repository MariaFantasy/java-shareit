package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.user.dto.UserResponseDto;

import java.time.LocalDateTime;

@lombok.Data
@lombok.AllArgsConstructor
public class BookingResponseDto {
    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private ItemResponseDto item;

    private UserResponseDto booker;

    private BookingState status;

}
