package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("""
            SELECT i
            FROM Item i
            WHERE (
                    LOWER(name) LIKE '%' || LOWER(?1) || '%'
                    OR
                    LOWER(description) LIKE '%' || LOWER(?1) || '%'
                )
                AND available
        """
    )
    List<Item> findByText(String text);

    List<Item> findByOwnerId(long ownerId);
}
