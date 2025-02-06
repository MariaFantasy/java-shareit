package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.dto.ItemToItemRequestDto;

import java.time.LocalDateTime;
import java.util.Collection;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemRequestResponseDto {
    private Long id;

    private String description;

    private LocalDateTime created;

    private Collection<ItemToItemRequestDto> items;
}
