package ru.practicum.shareit.item.model;

import ru.practicum.shareit.user.model.User;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode(of = { "id" })
public class Item {
    private Long id;

    private User owner;

    private String name;

    private String description;

    private boolean available;
}
