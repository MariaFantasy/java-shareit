package ru.practicum.shareit.booking.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.dto.BookingStateDto;
import ru.practicum.shareit.booking.mapper.BookingDtoMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("bookingServiceImpl")
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserService userService;
    private final ItemService itemService;
    private final BookingRepository bookingRepository;
    private final UserDtoMapper userDtoMapper;
    private final ItemDtoMapper itemDtoMapper;
    private final BookingDtoMapper bookingDtoMapper;

    @Override
    public Collection<BookingResponseDto> findByUserId(Long userId, BookingStateDto stateDto) {
        final UserResponseDto userDto = userService.findById(userId);
        switch (stateDto) {
            case BookingStateDto.ALL:
                return bookingRepository.findAllByUserId(userId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(HashSet::new));
            case BookingStateDto.CURRENT:
                return bookingRepository.findCurrentByUserId(userId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(HashSet::new));
            case BookingStateDto.PAST:
                return bookingRepository.findPastByUserId(userId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(HashSet::new));
            case BookingStateDto.FUTURE:
                return bookingRepository.findFutureByUserId(userId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(HashSet::new));
            case BookingStateDto.WAITING:
                return bookingRepository.findWaitingByUserId(userId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(HashSet::new));
            case BookingStateDto.REJECTED:
                return bookingRepository.findRejectedByUserId(userId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(HashSet::new));
            default:
                throw new ValidationException("Статус заказа " + stateDto + " еще не обрабатывется.");
        }
    }

    @Override
    public Collection<BookingResponseDto> findByOwnerId(Long ownerId, BookingStateDto stateDto) {
        final UserResponseDto userDto = userService.findById(ownerId);
        switch (stateDto) {
            case BookingStateDto.ALL:
                return bookingRepository.findAllByOwnerId(ownerId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(ArrayList::new));
            case BookingStateDto.CURRENT:
                return bookingRepository.findCurrentByOwnerId(ownerId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(ArrayList::new));
            case BookingStateDto.PAST:
                return bookingRepository.findPastByOwnerId(ownerId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(ArrayList::new));
            case BookingStateDto.FUTURE:
                return bookingRepository.findFutureByOwnerId(ownerId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(ArrayList::new));
            case BookingStateDto.WAITING:
                return bookingRepository.findWaitingByOwnerId(ownerId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(ArrayList::new));
            case BookingStateDto.REJECTED:
                return bookingRepository.findRejectedByOwnerId(ownerId).stream()
                        .map(booking -> bookingDtoMapper.mapToResponseDto(userDto, itemDtoMapper.mapToResponseDto(booking.getItem()), booking))
                        .collect(Collectors.toCollection(ArrayList::new));
            default:
                throw new ValidationException("Статус заказа " + stateDto + " еще не обрабатывется.");
        }
    }

    @Override
    public BookingResponseDto findById(Long userId, Long bookingId) {
        final UserResponseDto userDto = userService.findById(userId);
        final Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Бронирование с id = " + bookingId + " не найдено.")
        );
        final ItemResponseDto itemDto = itemDtoMapper.mapToResponseDto(booking.getItem());
        return bookingDtoMapper.mapToResponseDto(userDto, itemDto, booking);
    }

    @Override
    public BookingResponseDto create(Long userId, BookingRequestDto bookingRequestDto) {
        final UserResponseDto userDto = userService.findById(userId);
        final ItemResponseDto itemDto = itemService.findById(bookingRequestDto.getItemId());
        if (!itemDto.getAvailable()) {
            throw new ValidationException("Запрос на бронирование недоступной для бронирования вещи.");
        }

        final User user = userDtoMapper.mapFromDto(userDto);
        final Item item = itemDtoMapper.mapFromDto(user, itemDto);
        final Booking booking = bookingDtoMapper.mapFromDto(user, item, bookingRequestDto);

        final Booking createdBooking = bookingRepository.save(booking);
        return bookingDtoMapper.mapToResponseDto(userDto, itemDto, createdBooking);
    }

    @Override
    @Transactional
    public BookingResponseDto approve(Long userId, Long bookingId, Boolean approved) {
        final Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Бронирование с id = " + bookingId + " не найдено.")
        );
        if (!userId.equals(booking.getItem().getOwner().getId())) {
            throw new AccessException("Бронирование пытается одобрить не хозяин вещи.");
        }
        final UserResponseDto userDto = userDtoMapper.mapToResponseDto(booking.getUser());
        final ItemResponseDto itemDto = itemDtoMapper.mapToResponseDto(booking.getItem());
        bookingRepository.approve(bookingId, approved);
        final Booking updatedBooking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Бронирование с id = " + bookingId + " не найдено.")
        );
        return bookingDtoMapper.mapToResponseDto(userDto, itemDto, updatedBooking);
    }
}
