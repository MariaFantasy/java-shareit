package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotNull;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class ItemRequestRequestDto {
    @NotNull
    private String description;
}
