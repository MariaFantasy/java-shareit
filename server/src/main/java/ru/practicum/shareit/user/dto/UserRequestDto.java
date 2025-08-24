package ru.practicum.shareit.user.dto;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode(of = { "email" })
public class UserRequestDto {
    private String name;

    private String email;
}
