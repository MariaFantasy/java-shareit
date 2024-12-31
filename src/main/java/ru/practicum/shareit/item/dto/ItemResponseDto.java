package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.user.model.User;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemCreateResponseDto {
    private Long id;

    private String name;

    private String description;

    private boolean available;
}
