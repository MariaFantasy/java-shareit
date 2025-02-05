package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemResponseDto {
    private Long id;

    private String description;

    private LocalDateTime created;

    private ItemResponseDto items;
}
