package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    @GetMapping("/search")
    public Collection<Item> findByText(@RequestParam String text) {
        log.info("Пришел GET запрос /items/search?text={}", text);
        final Collection<Item> items = itemService.findByText(text);
        log.info("Отправлен ответ GET /items/search?text={} c телом: {}", text, items);
        return items;
    }

    @GetMapping
    public Collection<Item> findByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришел GET запрос /items с user_id = {}", userId);
        final Collection<Item> items = itemService.findByUserId(userId);
        log.info("Отправлен ответ GET /items с user_id = {} c телом: {}", userId, items);
        return items;
    }

    @GetMapping("/{itemId}")
    public Item findById(@PathVariable Long itemId) {
        log.info("Пришел GET запрос /items/{}", itemId);
        final Item item = itemService.findById(itemId);
        log.info("Отправлен ответ GET /items/{} с телом: {}", itemId, item);
        return item;
    }

    @PostMapping
    public Item create(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("Пришел POST запрос /items с userId = {} и телом: {}", userId, itemDto);
        final Item createdItem = itemService.create(userId, itemDto);
        log.info("Отпавлен ответ POST /items с телом: {}", createdItem);
        return createdItem;
    }

    @PatchMapping("/{itemId}")
    public Item update(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("Пришел PATCH запрос /items/{} с userId = {} и телом: {}", itemId, userId, itemDto);
        final Item updatedItem = itemService.update(itemId, userId, itemDto);
        log.info("Отпавлен ответ PATCH /items/{} с телом: {}", itemId, updatedItem);
        return updatedItem;
    }
}
