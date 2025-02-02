package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class CommentRequestDto {
    @NotBlank
    private String text;
}
