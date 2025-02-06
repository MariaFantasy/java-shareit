package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private static final String USER_ID_HEADER_NAME = "X-Sharer-User-Id";
    private final ItemClient itemClient;

    @GetMapping("/search")
    public ResponseEntity<Object> getBySubstring(@RequestParam String text) {
        log.info("Get items by substring={}", text);
        return itemClient.getBySubstring(text);
    }

    @GetMapping
    public ResponseEntity<Object> getByUserId(@RequestHeader(USER_ID_HEADER_NAME) Long userId) {
        log.info("Get items by user={}", userId);
        return itemClient.getByUserId(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable Long itemId) {
        log.info("Get item={}", itemId);
        return itemClient.getItem(itemId);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(USER_ID_HEADER_NAME) Long userId,
                                             @RequestBody @Valid ItemDto itemDto) {
        log.info("Creating item {} by user={}", itemDto, userId);
        return itemClient.createItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(USER_ID_HEADER_NAME) Long userId,
                                             @PathVariable Long itemId,
                                             @RequestBody @Valid ItemDto itemDto) {
        log.info("Updating item={} by user={} with new body {}", itemId, userId, itemDto);
        return itemClient.updateItem(itemId, userId, itemDto);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(USER_ID_HEADER_NAME) Long userId,
                                             @PathVariable Long itemId,
                                             @RequestBody @Valid CommentDto commentDto) {
        log.info("Creating item's comment {} for item={} by user={}", commentDto, itemId, userId);
        return itemClient.createComment(itemId, userId, commentDto);
    }
}
