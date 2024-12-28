package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Component
public class ItemDtoMapper {
    public Item mapFromDto(User user, ItemDto itemDto) {
        final Item item = new Item(
                null,
                user,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable()
        );
        return item;
    }
}
