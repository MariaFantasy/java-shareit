package ru.practicum.shareit.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemToItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class ItemRequestDtoMapper {

    public ItemRequest mapFromDto(User user, ItemRequestRequestDto itemRequestRequestDto, LocalDateTime created) {
        final ItemRequest itemRequest = new ItemRequest(
                null,
                user,
                itemRequestRequestDto.getDescription(),
                created
        );
        return itemRequest;
    }

    public ItemRequestResponseDto mapToResponseDto(Collection<ItemToItemRequestDto> items, ItemRequest itemRequest) {
        final ItemRequestResponseDto itemRequestResponseDto = new ItemRequestResponseDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getCreatedDate(),
                items
        );
        return itemRequestResponseDto;
    }
}
