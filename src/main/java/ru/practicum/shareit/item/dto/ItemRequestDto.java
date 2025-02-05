package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Boolean available;

    private Long requestId;
}
