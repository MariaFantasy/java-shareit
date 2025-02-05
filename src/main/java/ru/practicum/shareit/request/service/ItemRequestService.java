package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.Collection;

public interface ItemRequestService {
    public Collection<ItemRequestResponseDto> findByUserId(Long userId);

    public Collection<ItemRequestResponseDto> findAnothersRequests(Long userId);

    public ItemRequestResponseDto findById(Long requestId);

    public ItemRequestResponseDto create(Long userId, ItemRequestRequestDto itemRequestRequestDto);
}
