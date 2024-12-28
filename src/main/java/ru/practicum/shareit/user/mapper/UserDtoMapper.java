package ru.practicum.shareit.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Component
public class UserDtoMapper {
    public User mapFromDto(UserDto userDto) {
        final User user = new User(
                null,
                userDto.getName(),
                userDto.getEmail()
        );
        return user;
    }
}
