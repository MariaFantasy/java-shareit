package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface ItemStorage {
    public Collection<Item> findByText(String text);

    public Collection<Item> findByUser(User user);

    public Item findById(Long itemId);

    public Item create(Item item);

    public Item update(Item item);
}
