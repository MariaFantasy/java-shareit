package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.dto.BookingStateDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.CommentDtoMapper;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service("itemServiceImpl")
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final UserDtoMapper userDtoMapper;
    private final ItemDtoMapper itemDtoMapper;
    private final CommentDtoMapper commentDtoMapper;

    @Lazy
    @Autowired
    private BookingService bookingService;
    @Lazy
    @Autowired
    private ItemRequestService itemRequestService;

    @Override
    public Collection<ItemResponseDto> findByText(String text) {
        if (text == null || text.isEmpty()) {
            return new HashSet<ItemResponseDto>();
        }
        return itemRepository.findByText(text).stream()
                .map(itemDtoMapper::mapToResponseDto)
                .peek(this::loadComments)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Collection<ItemResponseDto> findByUserId(Long userId) {
        final UserResponseDto user = userService.findById(userId);
        return itemRepository.findByOwnerId(user.getId()).stream()
                .map(item -> itemDtoMapper.mapToResponseDto(item,
                        getLastBooking(item.getOwner().getId(), item.getId()),
                        getNextBooking(item.getOwner().getId(), item.getId()))
                )
                .peek(this::loadComments)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private LocalDateTime getLastBooking(Long ownerId, Long itemId) {
        LocalDateTime lastBooking = bookingService.findByOwnerId(ownerId, BookingStateDto.ALL).stream()
                .filter(booking -> booking.getItem().getId().equals(itemId))
                .map(BookingResponseDto::getStart)
                .filter(datetime -> !datetime.isAfter(LocalDateTime.now()))
                .max(LocalDateTime::compareTo)
                .orElse(null);
        return lastBooking;
    }

    private LocalDateTime getNextBooking(Long ownerId, Long itemId) {
        LocalDateTime lastBooking = bookingService.findByOwnerId(ownerId, BookingStateDto.ALL).stream()
                .filter(booking -> booking.getItem().getId().equals(itemId))
                .map(BookingResponseDto::getStart)
                .filter(datetime -> datetime.isAfter(LocalDateTime.now()))
                .max(LocalDateTime::compareTo)
                .orElse(null);
        return lastBooking;
    }

    @Override
    public ItemResponseDto findById(Long itemId) {
        final Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Вещь с id = " + itemId + " не найдена.")
        );

        ItemResponseDto itemResponseDto = itemDtoMapper.mapToResponseDto(item);
        loadComments(itemResponseDto);
        return itemResponseDto;
    }

    @Override
    public Collection<ItemToItemRequestDto> findByRequestId(Long requestId) {
        final Collection<Item> items = itemRepository.findByItemRequestId(requestId);
        return items.stream()
                .map(itemDtoMapper::mapToItemRequestDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ItemResponseDto create(Long userId, ItemRequestDto itemRequestDto) {
        final User user = userDtoMapper.mapFromDto(userService.findById(userId));
        if (itemRequestDto.getName() == null || itemRequestDto.getName().isEmpty() || itemRequestDto.getDescription() == null || itemRequestDto.getAvailable() == null) {
            throw new ValidationException("Описание вещи заполнено неполностью для создания.");
        }
        final Item item = itemRepository.save(itemDtoMapper.mapFromDto(user, itemRequestDto));
        return itemDtoMapper.mapToResponseDto(item);
    }

    @Override
    public ItemResponseDto update(Long itemId, Long userId, ItemRequestDto itemRequestDto) {
        final Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Вещь с id = " + itemId + " не найдена.")
        );
        final UserResponseDto user = userService.findById(userId);
        if (!item.getOwner().equals(userDtoMapper.mapFromDto(user))) {
            throw new ValidationException("Обновить вещь пытается не владелец.");
        }
        if (itemRequestDto.getName() != null) {
            item.setName(itemRequestDto.getName());
        }
        if (itemRequestDto.getDescription() != null) {
            item.setDescription(itemRequestDto.getDescription());
        }
        if (itemRequestDto.getAvailable() != null) {
            item.setAvailable(itemRequestDto.getAvailable());
        }
        final Item updatedItem = itemRepository.save(item);
        return itemDtoMapper.mapToResponseDto(updatedItem);
    }

    @Override
    @Modifying(clearAutomatically = true)
    public CommentResponseDto addComment(Long userId, Long itemId, CommentRequestDto commentRequestDto) {
        final User user = userDtoMapper.mapFromDto(userService.findById(userId));
        final Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Вещь с id = " + itemId + " не найдена.")
        );
        Collection<BookingResponseDto> bookings = bookingService.findByUserId(userId, BookingStateDto.PAST);
        bookings.stream()
                .filter(booking -> booking.getItem().getId().equals(itemId))
                .filter(booking -> booking.getEnd().isAfter(LocalDateTime.now().minusMinutes(1)))
                .findAny().orElseThrow(
                        () -> new ValidationException("Вещь с id = " + itemId + " не найдена среди прошлых бронирований пользователя " + userId + ".")
                );
        final Comment comment = commentRepository.save(commentDtoMapper.mapFromDto(item, user, commentRequestDto, LocalDateTime.now()));
        return commentDtoMapper.mapToResponseDto(comment);
    }

    private void loadComments(ItemResponseDto item) {
        Collection<CommentResponseDto> comments = commentRepository.findByItemId(item.getId()).stream()
                        .map(commentDtoMapper::mapToResponseDto)
                        .collect(Collectors.toCollection(ArrayList::new));
        item.setComments(comments);
    }
}
