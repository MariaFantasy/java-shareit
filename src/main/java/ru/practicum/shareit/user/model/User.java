package ru.practicum.shareit.user.model;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode(of = { "id" })
public class User {
    private Long id;

    private String name;

    private String email;
}
