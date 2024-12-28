package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    public Collection<Item> findByText(String text);

    public Collection<Item> findByUserId(Long userId);

    public Item findById(Long itemId);

    public Item create(Long userId, ItemDto itemDto);

    public Item update(Long itemId, Long userId, ItemDto itemDto);
}
