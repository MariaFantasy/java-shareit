package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;

import java.util.Collection;

public interface UserService {
    public Collection<UserResponseDto> findAll();

    public UserResponseDto findById(Long userId);

    public UserResponseDto create(UserRequestDto userRequestDto);

    public UserResponseDto update(Long userId, UserRequestDto userRequestDto);

    public UserResponseDto delete(Long userId);
}
