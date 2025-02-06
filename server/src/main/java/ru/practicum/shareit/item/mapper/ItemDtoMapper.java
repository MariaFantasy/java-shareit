package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.dto.ItemToItemRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Component
public class ItemDtoMapper {

    public Item mapFromDto(User user, ItemRequestDto itemRequestDto) {
        final Item item = new Item(
                null,
                user,
                itemRequestDto.getRequestId(),
                itemRequestDto.getName(),
                itemRequestDto.getDescription(),
                itemRequestDto.getAvailable()
        );
        return item;
    }

    public Item mapFromDto(User user, ItemResponseDto itemResponseDto) {
        final Item item = new Item(
                itemResponseDto.getId(),
                user,
                null,
                itemResponseDto.getName(),
                itemResponseDto.getDescription(),
                itemResponseDto.getAvailable()
        );
        return item;
    }

    public ItemResponseDto mapToResponseDto(Item item, LocalDateTime lastBooking, LocalDateTime nextBooking) {
        final ItemResponseDto itemDto = new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                null,
                lastBooking,
                nextBooking
        );
        return itemDto;
    }

    public ItemResponseDto mapToResponseDto(Item item) {
        final ItemResponseDto itemDto = new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                null,
                null,
                null
        );
        return itemDto;
    }

    public ItemToItemRequestDto mapToItemRequestDto(Item item) {
        final ItemToItemRequestDto itemDto = new ItemToItemRequestDto(
                item.getId(),
                item.getName(),
                item.getOwner().getId()
        );
        return itemDto;
    }
}
