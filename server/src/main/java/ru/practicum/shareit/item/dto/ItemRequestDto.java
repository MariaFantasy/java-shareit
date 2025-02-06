package ru.practicum.shareit.item.dto;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemRequestDto {
    private String name;

    private String description;

    private Boolean available;

    private Long requestId;
}
