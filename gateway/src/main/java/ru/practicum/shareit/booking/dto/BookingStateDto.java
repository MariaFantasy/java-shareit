package ru.practicum.shareit.booking.dto;

import java.util.Optional;

public enum BookingStateDto {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static Optional<BookingStateDto> from(String stringState) {
        for (BookingStateDto state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
