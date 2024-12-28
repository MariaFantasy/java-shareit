package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserStorage {
    public Collection<User> findAll();

    public User findById(Long userId);

    public User findByEmail(String email);

    public User create(User user);

    public User update(User user);

    public User delete(Long userId);
}
