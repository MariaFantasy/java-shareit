package ru.practicum.shareit.item.dto;

import java.time.LocalDateTime;

@lombok.Data
@lombok.AllArgsConstructor
public class CommentResponseDto {
    private Long id;

    private String text;

    private String authorName;

    private LocalDateTime created;
}
