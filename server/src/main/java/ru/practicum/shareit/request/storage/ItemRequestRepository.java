package ru.practicum.shareit.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findByUserId(long userId);

    @Query(value = """
            SELECT *
            FROM requests AS r
            WHERE user_id <> ?1
        """,
        nativeQuery = true
    )
    List<ItemRequest> findAnothersRequests(long userId);
}
