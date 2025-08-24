package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.AlreadyExistException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emailUniqSet = new HashSet<>();
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
    public User create(User user) {
        final String email = user.getEmail();
        if (emailUniqSet.contains(email)) {
            throw new AlreadyExistException("Пользователь с email = " + email + " уже существует.");
        }
        final long userId = getNextId();
        user.setId(userId);
        users.put(userId, user);
        emailUniqSet.add(user.getEmail());
        return user;
    }

    @Override
    public User update(User user) {
        final String email = user.getEmail();
        final Long userId = user.getId();
        final User oldUser = users.get(userId);
        if (!email.equals(oldUser.getEmail())) {
            if (emailUniqSet.contains(email)) {
                throw new AlreadyExistException("Пользователь с email = " + email + " уже существует.");
            }
            emailUniqSet.remove(oldUser.getEmail());
            emailUniqSet.add(email);
        }
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
