package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.dto.BookingStateDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.user.UserController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private static final String USER_ID_HEADER_NAME = "X-Sharer-User-Id";
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final BookingService bookingService;

    @GetMapping
    public Collection<BookingResponseDto> findByUserId(@RequestHeader(USER_ID_HEADER_NAME) Long userId, @RequestParam(required = false, defaultValue = "ALL") BookingStateDto stateDto) {
        log.info("Пришел POST запрос /bookings?state={} с userId = {}", stateDto, userId);
        final Collection<BookingResponseDto> bookings = bookingService.findByUserId(userId, stateDto);
        log.info("Отправлен ответ POST /bookings?state={} с userId = {} с телом: {}", stateDto, userId, bookings);
        return bookings;
    }

    @GetMapping("/owner")
    public Collection<BookingResponseDto> findByOwnerId(@RequestHeader(USER_ID_HEADER_NAME) Long ownerId, @RequestParam(required = false, defaultValue = "ALL") BookingStateDto stateDto) {
        log.info("Пришел POST запрос /bookings/owner?state={} с userId = {}", stateDto, ownerId);
        final Collection<BookingResponseDto> bookings = bookingService.findByOwnerId(ownerId, stateDto);
        log.info("Отправлен ответ POST /bookings/owner?state={} с телом: {}", stateDto, ownerId, bookings);
        return bookings;
    }

    @GetMapping("/{bookingId}")
    public BookingResponseDto findById(@RequestHeader(USER_ID_HEADER_NAME) Long userId, @PathVariable Long bookingId) {
        log.info("Пришел POST запрос /bookings/{} с userId = {}", bookingId, userId);
        final BookingResponseDto booking = bookingService.findById(userId, bookingId);
        log.info("Отправлен ответ POST /bookings/{} с телом: {}", bookingId, booking);
        return booking;
    }

    @PostMapping
    public BookingResponseDto create(@RequestHeader(USER_ID_HEADER_NAME) Long userId, @Valid @RequestBody BookingRequestDto bookingRequestDto) {
        log.info("Пришел POST запрос /bookings с userId = {} и телом: {}", userId, bookingRequestDto);
        final BookingResponseDto createdBooking = bookingService.create(userId, bookingRequestDto);
        log.info("Отправлен ответ POST /bookings с телом: {}", createdBooking);
        return createdBooking;
    }

    @PatchMapping("/{bookingId}")
    public BookingResponseDto approve(@RequestHeader(USER_ID_HEADER_NAME) Long userId, @PathVariable Long bookingId, @RequestParam Boolean approved) {
        log.info("Пришел PATCH запрос /bookings/{}?approved={} с userId = {}", bookingId, approved, userId);
        final BookingResponseDto booking = bookingService.approve(userId, bookingId, approved);
        log.info("Отправлен ответ PATCH /bookings{}?approved={} с телом: {}", bookingId, approved, booking);
        return booking;
    }
}
