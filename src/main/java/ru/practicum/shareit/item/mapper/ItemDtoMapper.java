package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserResponseDto;

@Component
public class ItemDtoMapper {
    public Item mapFromDto(UserResponseDto user, ItemRequestDto itemRequestDto) {
        final Item item = new Item(
                null,
                user,
                itemRequestDto.getName(),
                itemRequestDto.getDescription(),
                itemRequestDto.getAvailable()
        );
        return item;
    }

    public ItemResponseDto mapToResponseDto(Item item) {
        final ItemResponseDto itemDto = new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable()
        );
        return itemDto;
    }
}
