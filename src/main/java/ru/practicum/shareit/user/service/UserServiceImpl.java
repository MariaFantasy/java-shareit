package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service("userServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Override
    public Collection<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(userDtoMapper::mapToResponseDto)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public UserResponseDto findById(Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь с id = " + userId + " не найден.")
        );
        return userDtoMapper.mapToResponseDto(user);
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        if (userRequestDto.getEmail() == null) {
            throw new ValidationException("Email нового пользователя пустой.");
        }
        final User user = userRepository.save(userDtoMapper.mapFromDto(userRequestDto));
        return userDtoMapper.mapToResponseDto(user);
    }

    @Override
    public UserResponseDto update(Long userId, UserRequestDto userRequestDto) {
        final User user = userDtoMapper.mapFromDto(userRequestDto);
        final User oldUser = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь с id = " + userId + " не найден.")
        );
        user.setId(oldUser.getId());
        if (userRequestDto.getName() == null) {
            user.setName(oldUser.getName());
        }
        if (userRequestDto.getEmail() == null) {
            user.setEmail(oldUser.getEmail());
        }

        final User updatedUser = userRepository.save(user);
        return userDtoMapper.mapToResponseDto(updatedUser);
    }

    @Override
    public UserResponseDto delete(Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь с id = " + userId + " не найден.")
        );
        userRepository.deleteById(user.getId());
        return userDtoMapper.mapToResponseDto(user);
    }
}
