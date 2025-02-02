package ru.practicum.shareit.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.model.User;

@Component
public class UserDtoMapper {
    public User mapFromDto(UserRequestDto userRequestDto) {
        final User user = new User(
                null,
                userRequestDto.getName(),
                userRequestDto.getEmail()
        );
        return user;
    }

    public User mapFromDto(UserResponseDto userResponseDto) {
        final User user = new User(
                userResponseDto.getId(),
                userResponseDto.getName(),
                userResponseDto.getEmail()
        );
        return user;
    }

    public UserResponseDto mapToResponseDto(User user) {
        final UserResponseDto userDto = new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
        return userDto;
    }
}
