package ru.practicum.shareit.item.dto;

import java.time.LocalDateTime;
import java.util.Collection;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemResponseDto {
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Collection<CommentResponseDto> comments;

    private LocalDateTime lastBooking;

    private LocalDateTime nextBooking;
}
