package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.mapper.ItemRequestDtoMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service("itemRequestServiceImpl")
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final UserService userService;
    private final ItemService itemService;
    private final ItemRequestRepository itemRequestRepository;
    private final UserDtoMapper userDtoMapper;
    private final ItemRequestDtoMapper itemRequestDtoMapper;

    @Override
    public Collection<ItemRequestResponseDto> findByUserId(Long userId) {
        final UserResponseDto userDto = userService.findById(userId);

        return itemRequestRepository.findByUserId(userId).stream()
                .map(itemRequest -> itemRequestDtoMapper.mapToResponseDto(itemService.findByRequestId(itemRequest.getId()), itemRequest))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Collection<ItemRequestResponseDto> findAnothersRequests(Long userId) {
        final UserResponseDto userDto = userService.findById(userId);

        return itemRequestRepository.findAnothersRequests(userId).stream()
                .map(itemRequest -> itemRequestDtoMapper.mapToResponseDto(itemService.findByRequestId(itemRequest.getId()), itemRequest))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ItemRequestResponseDto findById(Long requestId) {
        final ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException("Запроса с id = " + requestId + " не найден.")
        );
        return itemRequestDtoMapper.mapToResponseDto(itemService.findByRequestId(itemRequest.getId()), itemRequest);
    }

    @Override
    public ItemRequestResponseDto create(Long userId, ItemRequestRequestDto itemRequestRequestDto) {
        final UserResponseDto userDto = userService.findById(userId);

        final User user = userDtoMapper.mapFromDto(userDto);
        final ItemRequest itemRequest = itemRequestDtoMapper.mapFromDto(user, itemRequestRequestDto, LocalDateTime.now());

        final ItemRequest createdItemRequest = itemRequestRepository.save(itemRequest);
        return itemRequestDtoMapper.mapToResponseDto(itemService.findByRequestId(itemRequest.getId()), itemRequest);
    }
}
