package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private static final String USER_ID_HEADER_NAME = "X-Sharer-User-Id";
    private static final Logger log = LoggerFactory.getLogger(ItemRequestController.class);
    private final ItemRequestService itemRequestService;

    @GetMapping
    public Collection<ItemRequestResponseDto> findByUserId(@RequestHeader(USER_ID_HEADER_NAME) Long userId) {
        log.info("Пришел GET запрос /bookings с userId = {}", userId);
        final Collection<ItemRequestResponseDto> requests = itemRequestService.findByUserId(userId);
        log.info("Отправлен ответ GET /bookings с userId = {} с телом: {}", userId, requests);
        return requests;
    }

    @GetMapping("/all")
    public Collection<ItemRequestResponseDto> findAnothersRequests(@RequestHeader(USER_ID_HEADER_NAME) Long userId) {
        log.info("Пришел GET запрос /bookings/all с userId = {}", userId);
        final Collection<ItemRequestResponseDto> requests = itemRequestService.findAnothersRequests(userId);
        log.info("Отправлен ответ GET /bookings/all с userId = {} с телом: {}", userId, requests);
        return requests;
    }

    @GetMapping("/{requestId}")
    public ItemRequestResponseDto findById(@RequestHeader(USER_ID_HEADER_NAME) Long userId, @PathVariable Long requestId) {
        log.info("Пришел GET запрос /bookings/{} с userId = {}", requestId, userId);
        final ItemRequestResponseDto request = itemRequestService.findById(requestId);
        log.info("Отправлен ответ GET /bookings/{} с userId = {} с телом: {}", requestId, userId, request);
        return request;
    }

    @PostMapping
    public ItemRequestResponseDto create(@RequestHeader(USER_ID_HEADER_NAME) Long userId, @Valid @RequestBody ItemRequestRequestDto itemRequestRequestDto) {
        log.info("Пришел POST запрос /requests с userId = {} и телом: {}", userId, itemRequestRequestDto);
        final ItemRequestResponseDto createdRequest = itemRequestService.create(userId, itemRequestRequestDto);
        log.info("Отправлен ответ POST /requests с телом: {}", createdRequest);
        return createdRequest;
    }
}
