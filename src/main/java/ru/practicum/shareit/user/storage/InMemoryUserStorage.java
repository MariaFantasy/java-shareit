package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long userCounter = 0;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(Long userId) {
        final User user = users.get(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            throw new ValidationException("Запрашивается пустой email.");
        }
        final Optional<User> user = users.values().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst();
        if (user.isEmpty()) {
            throw new NotFoundException("Пользователь с email = " + email + " не найден.");
        }
        return user.get();
    }

    @Override
    public User create(User user) {
        final long userId = getNextId();
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public User update(User user) {
        final Long userId = user.getId();
        users.put(userId, user);
        return user;
    }

    @Override
    public User delete(Long userId) {
        final User user = findById(userId);
        users.remove(userId);
        return user;
    }

    private long getNextId() {
        return ++userCounter;
    }
}
