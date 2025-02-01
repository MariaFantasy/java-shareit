package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private static final String USER_ID_HEADER_NAME = "X-Sharer-User-Id";
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    @GetMapping("/search")
    public Collection<ItemResponseDto> findByText(@RequestParam String text) {
        log.info("Пришел GET запрос /items/search?text={}", text);
        final Collection<ItemResponseDto> items = itemService.findByText(text);
        log.info("Отправлен ответ GET /items/search?text={} c телом: {}", text, items);
        return items;
    }

    @GetMapping
    public Collection<ItemResponseDto> findByUserId(@RequestHeader(USER_ID_HEADER_NAME) Long userId) {
        log.info("Пришел GET запрос /items с user_id = {}", userId);
        final Collection<ItemResponseDto> items = itemService.findByUserId(userId);
        log.info("Отправлен ответ GET /items с user_id = {} c телом: {}", userId, items);
        return items;
    }

    @GetMapping("/{itemId}")
    public ItemResponseDto findById(@PathVariable Long itemId) {
        log.info("Пришел GET запрос /items/{}", itemId);
        final ItemResponseDto item = itemService.findById(itemId);
        log.info("Отправлен ответ GET /items/{} с телом: {}", itemId, item);
        return item;
    }

    @PostMapping
    public ItemResponseDto create(@RequestHeader(USER_ID_HEADER_NAME) Long userId, @Valid @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Пришел POST запрос /items с userId = {} и телом: {}", userId, itemRequestDto);
        final ItemResponseDto createdItem = itemService.create(userId, itemRequestDto);
        log.info("Отпавлен ответ POST /items с телом: {}", createdItem);
        return createdItem;
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto update(@PathVariable Long itemId, @RequestHeader(USER_ID_HEADER_NAME) Long userId, @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Пришел PATCH запрос /items/{} с userId = {} и телом: {}", itemId, userId, itemRequestDto);
        final ItemResponseDto updatedItem = itemService.update(itemId, userId, itemRequestDto);
        log.info("Отпавлен ответ PATCH /items/{} с телом: {}", itemId, updatedItem);
        return updatedItem;
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponseDto addComment(@PathVariable Long itemId, @RequestHeader(USER_ID_HEADER_NAME) Long userId, @Valid @RequestBody CommentRequestDto commentRequestDto) {
        try {
            log.info("Пришел POST запрос /items/{}/comment с userId = {} и телом: {}", itemId, userId, commentRequestDto);
            final CommentResponseDto createdComment = itemService.addComment(userId, itemId, commentRequestDto);
            log.info("Отпавлен ответ POST /items/{}/comment с телом: {}", itemId, createdComment);
            return createdComment;
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }
}
