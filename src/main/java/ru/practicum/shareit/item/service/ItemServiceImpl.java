package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service("itemServiceImpl")
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemStorage itemStorage;
    private final ItemDtoMapper itemDtoMapper;

    @Override
    public Collection<ItemResponseDto> findByText(String text) {
        if (text == null || text.isEmpty()) {
            return new HashSet<ItemResponseDto>();
        }
        return itemStorage.findByText(text).stream()
                .map(itemDtoMapper::mapToResponseDto)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Collection<ItemResponseDto> findByUserId(Long userId) {
        final UserResponseDto user = userService.findById(userId);
        return itemStorage.findByUser(user.getId()).stream()
                .map(itemDtoMapper::mapToResponseDto)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public ItemResponseDto findById(Long itemId) {
        final Item item = itemStorage.findById(itemId);
        return itemDtoMapper.mapToResponseDto(item);
    }

    @Override
    public ItemResponseDto create(Long userId, ItemRequestDto itemRequestDto) {
        final UserResponseDto user = userService.findById(userId);
        if (itemRequestDto.getName() == null || itemRequestDto.getName().isEmpty() || itemRequestDto.getDescription() == null || itemRequestDto.getAvailable() == null) {
            throw new ValidationException("Описание вещи заполнено неполностью для создания.");
        }
        final Item item = itemStorage.create(itemDtoMapper.mapFromDto(user, itemRequestDto));
        return itemDtoMapper.mapToResponseDto(item);
    }

    @Override
    public ItemResponseDto update(Long itemId, Long userId, ItemRequestDto itemRequestDto) {
        final Item item = itemStorage.findById(itemId);
        final UserResponseDto user = userService.findById(userId);
        if (!item.getOwner().equals(user)) {
            throw new ValidationException("Обновить вещь пытается не владелец.");
        }
        if (itemRequestDto.getName() != null) {
            item.setName(itemRequestDto.getName());
        }
        if (itemRequestDto.getDescription() != null) {
            item.setDescription(itemRequestDto.getDescription());
        }
        if (itemRequestDto.getAvailable() != null) {
            item.setAvailable(itemRequestDto.getAvailable());
        }
        final Item updatedItem = itemStorage.update(item);
        return itemDtoMapper.mapToResponseDto(updatedItem);
    }
}
