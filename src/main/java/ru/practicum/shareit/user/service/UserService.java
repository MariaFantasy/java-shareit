package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserService {
    public Collection<User> findAll();

    public User findById(Long userId);

    public User create(UserDto userDto);

    public User update(Long userId, UserDto userDto);

    public User delete(Long userId);
}
