package ru.practicum.shareit.item.storage;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = """
            SELECT c.*
            FROM comments AS c
            WHERE c.item_id = ?1
        """,
        nativeQuery = true
    )
    List<Comment> findByItemId(Long itemId);
}
