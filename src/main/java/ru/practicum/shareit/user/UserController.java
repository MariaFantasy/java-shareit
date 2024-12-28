package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
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
    public Collection<User> findAll() {
        log.info("Пришел GET запрос /users");
        final Collection<User> users = userService.findAll();
        log.info("Отправлен ответ GET /users с телом: {}", users);
        return users;
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable Long userId) {
        log.info("Пришел GET зарпос /users/{}", userId);
        final User user = userService.findById(userId);
        log.info("Отправлен ответ GET /users/{} с телом: {}", userId, user);
        return user;
    }

    @PostMapping
    public User create(@Valid @RequestBody UserDto userDto) {
        log.info("Пришел POST запрос /users с телом: {}", userDto);
        final User createdUser = userService.create(userDto);
        log.info("Отправлен ответ POST /users с телом: {}", createdUser);
        return createdUser;
    }

    @PatchMapping("/{userId}")
    public User update(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        log.info("Пришел PATCH запрос /users/{} с телом: {}", userId, userDto);
        final User updatedUser = userService.update(userId, userDto);
        log.info("Отправлен ответ PATCH /users/{} с телом: {}", userId, updatedUser);
        return updatedUser;
    }

    @DeleteMapping("/{userId}")
    public User delete(@PathVariable Long userId) {
        log.info("Пришел DELETE зарпос /users/{}", userId);
        final User user = userService.delete(userId);
        log.info("Отправлен ответ DELETE /users/{} с телом: {}", userId, user);
        return user;
    }
}
