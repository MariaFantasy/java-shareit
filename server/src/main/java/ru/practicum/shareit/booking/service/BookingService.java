package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.dto.BookingStateDto;

import java.util.Collection;

public interface BookingService {
    public Collection<BookingResponseDto> findByUserId(Long userId, BookingStateDto stateDto);

    public Collection<BookingResponseDto> findByOwnerId(Long ownerId, BookingStateDto stateDto);

    public BookingResponseDto findById(Long userId, Long bookingId);

    public BookingResponseDto create(Long userId, BookingRequestDto bookingRequestDto);

    public BookingResponseDto approve(Long userId, Long bookingId, Boolean approved);
}
