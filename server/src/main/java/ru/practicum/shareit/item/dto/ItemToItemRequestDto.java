package ru.practicum.shareit.item.dto;

@lombok.Data
@lombok.AllArgsConstructor
public class ItemToItemRequestDto {
    private Long id;

    private String name;

    private Long userId;
}
