package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode(of = { "email" })
public class UserDto {
    private String name;

    @Email(message = "Email пользователя передан в некорректном формате")
    private String email;
}