package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotNull;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemRequestDto {
    @NotNull
    private String description;
}
