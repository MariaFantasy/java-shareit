package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component("inMemoryItemStorage")
public class InMemoryItemStorage implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();
    private long itemCounter = 0;

    @Override
    public Collection<Item> findByText(String text) {
        return items.values().stream()
                .filter(Item::isAvailable)
                .filter(i -> i.getDescription().toLowerCase().contains(text.toLowerCase()) || i.getName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toCollection(HashSet<Item>::new));
    }

    @Override
    public Collection<Item> findByUser(Long userId) {
        return items.values().stream()
                .filter(i -> i.getOwner().getId().equals(userId))
                .collect(Collectors.toCollection(HashSet<Item>::new));
    }

    @Override
    public Item findById(Long itemId) {
        final Item item = items.get(itemId);
        if (item == null) {
            throw new NotFoundException("Вещь с id = " + itemId + " не найден.");
        }
        return item;
    }

    @Override
    public Item create(Item item) {
        final long itemId = getNextId();
        item.setId(itemId);
        items.put(itemId, item);
        return item;
    }

    @Override
    public Item update(Item item) {
        final Long itemId = item.getId();
        items.put(itemId, item);
        return item;
    }

    private long getNextId() {
        return ++itemCounter;
    }
}
