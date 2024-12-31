package ru.practicum.shareit.item.dto;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemDto {
    private String name;

    private String description;

    private Boolean available;
}
