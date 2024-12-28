package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.HashSet;

@Service("itemServiceImpl")
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemStorage itemStorage;
    private final ItemDtoMapper itemDtoMapper;

    @Override
    public Collection<Item> findByText(String text) {
        if (text == null || text.isEmpty()) {
            return new HashSet<Item>();
        }
        return itemStorage.findByText(text);
    }

    @Override
    public Collection<Item> findByUserId(Long userId) {
        final User user = userService.findById(userId);
        return itemStorage.findByUser(user);
    }

    @Override
    public Item findById(Long itemId) {
        final Item item = itemStorage.findById(itemId);
        return item;
    }

    @Override
    public Item create(Long userId, ItemDto itemDto) {
        final User user = userService.findById(userId);
        if (itemDto.getName() == null || itemDto.getName().isEmpty() || itemDto.getDescription() == null || itemDto.getAvailable() == null) {
            throw new ValidationException("Описание вещи заполнено неполностью для создания.");
        }
        final Item item = itemStorage.create(itemDtoMapper.mapFromDto(user, itemDto));
        return item;
    }

    @Override
    public Item update(Long itemId, Long userId, ItemDto itemDto) {
        final Item item = itemStorage.findById(itemId);
        final User user = userService.findById(userId);
        if (!item.getOwner().equals(user)) {
            throw new ValidationException("Обновить вещь пытается не владелец.");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        final Item updatedItem = itemStorage.update(item);
        return updatedItem;
    }
}
