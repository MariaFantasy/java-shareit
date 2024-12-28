package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AlreadyExistException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;

@Service("userServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final UserDtoMapper userDtoMapper;

    @Override
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User findById(Long userId) {
        final User user = userStorage.findById(userId);
        return user;
    }

    @Override
    public User create(UserDto userDto) {
        if (userDto.getEmail() == null) {
            throw new ValidationException("Email нового пользователя пустой.");
        }
        try {
            final User oldUser = userStorage.findByEmail(userDto.getEmail());
            throw new AlreadyExistException("Пользователь с email = " + userDto.getEmail() + " уже существует.");
        } catch (NotFoundException e) {
            final User user = userStorage.create(userDtoMapper.mapFromDto(userDto));
            return user;
        }
    }

    @Override
    public User update(Long userId, UserDto userDto) {
        final User user = userStorage.findById(userId);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            try {
                final User oldUser = userStorage.findByEmail(userDto.getEmail());
                if (oldUser.getId().equals(userId)) {
                    throw new NotFoundException("Тот же самый пользователь.");
                }
                throw new AlreadyExistException("Пользователь с email = " + userDto.getEmail() + " уже существует.");
            } catch (NotFoundException e) {
                user.setEmail(userDto.getEmail());
            }
        }

        final User updatedUser = userStorage.update(user);
        return updatedUser;
    }

    @Override
    public User delete(Long userId) {
        final User user = userStorage.findById(userId);
        userStorage.delete(user.getId());
        return user;
    }
}
