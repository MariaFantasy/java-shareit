package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
    private static final String USER_ID_HEADER_NAME = "X-Sharer-User-Id";
    private final RequestClient requestClient;

    @GetMapping
    public ResponseEntity<Object> getByUserId(@RequestHeader(USER_ID_HEADER_NAME) Long userId) {
        log.info("Get requests by user={}", userId);
        return requestClient.getByUserId(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAnothersRequests(@RequestHeader(USER_ID_HEADER_NAME) Long userId) {
        log.info("Get another's requests by user={}", userId);
        return requestClient.getAnothersRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@RequestHeader(USER_ID_HEADER_NAME) Long userId,
                                             @PathVariable Long requestId) {
        log.info("Get request={} by user={}", requestId, userId);
        return requestClient.getRequest(userId, requestId);
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(USER_ID_HEADER_NAME) Long userId,
                                             @RequestBody @Valid RequestDto requestDto) {
        log.info("Creating request {} by user={}", requestDto, userId);
        return requestClient.createRequest(userId, requestDto);
    }
}
