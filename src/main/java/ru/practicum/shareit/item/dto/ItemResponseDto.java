package ru.practicum.shareit.item.dto;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemResponseDto {
    private Long id;

    private String name;

    private String description;

    private boolean available;
}
