package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;

import java.util.Collection;

public interface ItemService {
    public Collection<ItemResponseDto> findByText(String text);

    public Collection<ItemResponseDto> findByUserId(Long userId);

    public ItemResponseDto findById(Long itemId);

    public ItemResponseDto create(Long userId, ItemRequestDto itemRequestDto);

    public ItemResponseDto update(Long itemId, Long userId, ItemRequestDto itemRequestDto);

    public CommentResponseDto addComment(Long userId, Long itemId, CommentRequestDto commentRequestDto);
}
