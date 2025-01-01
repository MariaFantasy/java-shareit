package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service("userServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final UserDtoMapper userDtoMapper;

    @Override
    public Collection<UserResponseDto> findAll() {
        return userStorage.findAll().stream()
                .map(userDtoMapper::mapToResponseDto)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public UserResponseDto findById(Long userId) {
        final User user = userStorage.findById(userId);
        return userDtoMapper.mapToResponseDto(user);
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        if (userRequestDto.getEmail() == null) {
            throw new ValidationException("Email нового пользователя пустой.");
        }
        final User user = userStorage.create(userDtoMapper.mapFromDto(userRequestDto));
        return userDtoMapper.mapToResponseDto(user);
    }

    @Override
    public UserResponseDto update(Long userId, UserRequestDto userRequestDto) {
        final User user = userDtoMapper.mapFromDto(userRequestDto);
        final User oldUser = userStorage.findById(userId);
        user.setId(oldUser.getId());
        if (userRequestDto.getName() == null) {
            user.setName(oldUser.getName());
        }
        if (userRequestDto.getEmail() == null) {
            user.setEmail(oldUser.getEmail());
        }

        final User updatedUser = userStorage.update(user);
        return userDtoMapper.mapToResponseDto(updatedUser);
    }

    @Override
    public UserResponseDto delete(Long userId) {
        final User user = userStorage.findById(userId);
        userStorage.delete(user.getId());
        return userDtoMapper.mapToResponseDto(user);
    }
}
