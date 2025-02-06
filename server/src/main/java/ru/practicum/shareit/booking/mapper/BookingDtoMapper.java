package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.model.User;

@Component
public class BookingDtoMapper {

    public Booking mapFromDto(User user, Item item, BookingRequestDto bookingRequestDto) {
        final Booking booking = new Booking(
                null,
                bookingRequestDto.getStart(),
                bookingRequestDto.getEnd(),
                item,
                user,
                BookingState.WAITING
        );
        return booking;
    }

    public BookingResponseDto mapToResponseDto(UserResponseDto userResponseDto, ItemResponseDto itemResponseDto, Booking booking) {
        final BookingResponseDto bookingResponseDto = new BookingResponseDto(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                itemResponseDto,
                userResponseDto,
                booking.getStatus()
        );
        return bookingResponseDto;
    }
}
