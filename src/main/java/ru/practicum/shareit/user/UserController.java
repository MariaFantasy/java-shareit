package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping
    public Collection<UserResponseDto> findAll() {
        log.info("Пришел GET запрос /users");
        final Collection<UserResponseDto> users = userService.findAll();
        log.info("Отправлен ответ GET /users с телом: {}", users);
        return users;
    }

    @GetMapping("/{userId}")
    public UserResponseDto findById(@PathVariable Long userId) {
        log.info("Пришел GET зарпос /users/{}", userId);
        final UserResponseDto user = userService.findById(userId);
        log.info("Отправлен ответ GET /users/{} с телом: {}", userId, user);
        return user;
    }

    @PostMapping
    public UserResponseDto create(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Пришел POST запрос /users с телом: {}", userRequestDto);
        final UserResponseDto createdUser = userService.create(userRequestDto);
        log.info("Отправлен ответ POST /users с телом: {}", createdUser);
        return createdUser;
    }

    @PatchMapping("/{userId}")
    public UserResponseDto update(@PathVariable Long userId, @Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Пришел PATCH запрос /users/{} с телом: {}", userId, userRequestDto);
        final UserResponseDto updatedUser = userService.update(userId, userRequestDto);
        log.info("Отправлен ответ PATCH /users/{} с телом: {}", userId, updatedUser);
        return updatedUser;
    }

    @DeleteMapping("/{userId}")
    public UserResponseDto delete(@PathVariable Long userId) {
        log.info("Пришел DELETE зарпос /users/{}", userId);
        final UserResponseDto user = userService.delete(userId);
        log.info("Отправлен ответ DELETE /users/{} с телом: {}", userId, user);
        return user;
    }
}
