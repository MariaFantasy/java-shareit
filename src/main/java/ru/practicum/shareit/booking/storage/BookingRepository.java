package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND b.user_id = ?1
            ORDER BY b.start_date DESC
        """,
        nativeQuery = true
    )
    List<Booking> findAllByUserId(long userId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND b.user_id = ?1
                AND b.start_date <= now()
                AND b.end_date > now()
            ORDER BY b.start_date DESC
        """,
            nativeQuery = true
    )
    List<Booking> findCurrentByUserId(long userId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND b.user_id = ?1
                AND b.end_date <= now()
            ORDER BY b.start_date DESC
        """,
            nativeQuery = true
    )
    List<Booking> findPastByUserId(long userId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND b.user_id = ?1
                AND b.start_date > now()
            ORDER BY b.start_date DESC
        """,
            nativeQuery = true
    )
    List<Booking> findFutureByUserId(long userId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND b.user_id = ?1
                AND b.status = "WAITING"
            ORDER BY b.start_date DESC
        """,
            nativeQuery = true
    )
    List<Booking> findWaitingByUserId(long userId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND b.user_id = ?1
                AND b.status = "REJECTED"
            ORDER BY b.start_date DESC
        """,
            nativeQuery = true
    )
    List<Booking> findRejectedByUserId(long userId);



    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND i.owner_id = ?1
            ORDER BY b.start_date DESC
        """,
        nativeQuery = true
    )
    List<Booking> findAllByOwnerId(Long ownerId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND i.owner_id = ?1
                AND b.start_date <= now()
                AND b.end_date > now()
            ORDER BY b.start_date DESC
        """,
        nativeQuery = true
    )
    List<Booking> findCurrentByOwnerId(Long ownerId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND i.owner_id = ?1
                AND b.end_date <= now()
            ORDER BY b.start_date DESC
        """,
        nativeQuery = true
    )
    List<Booking> findPastByOwnerId(Long ownerId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND i.owner_id = ?1
                AND b.start_date > now()
            ORDER BY b.start_date DESC
        """,
        nativeQuery = true
    )
    List<Booking> findFutureByOwnerId(Long ownerId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND i.owner_id = ?1
                AND b.status = "WAITING"
            ORDER BY b.start_date DESC
        """,
        nativeQuery = true
    )
    List<Booking> findWaitingByOwnerId(Long ownerId);

    @Query(value = """
            SELECT
                b.*
            FROM bookings AS b
            INNER JOIN items AS i
                ON b.item_id = i.item_id
                AND i.owner_id = ?1
                AND b.status = "REJECTED"
            ORDER BY b.start_date DESC
        """,
        nativeQuery = true
    )
    List<Booking> findRejectedByOwnerId(Long ownerId);


    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE bookings b
            SET
                b.status = CASE WHEN ?2 = 'True' THEN 'APPROVED' ELSE 'REJECTED' END
            WHERE b.booking_id = ?1
        """,
        nativeQuery = true
    )
    void approve(Long bookingId, Boolean approved);
}
