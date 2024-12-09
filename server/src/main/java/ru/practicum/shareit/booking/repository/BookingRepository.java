package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Получение информации о всех бронированиях пользователя
    Collection<Booking> findAllByBookerId(final Long userId);

    // Получение информации о всех бронированиях владельца вещей
    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.item.owner.id = :ownerId
            """)
    Collection<Booking> findAllByItemOwnerId(@Param("ownerId") final Long userId);

    // Получение информации о бронировании по идентификатору пользователя и идентификатору вещи
    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.booker.id = :userId
                AND b.item.id = :itemId
            """)
    Optional<Booking> findByBookerIdAndItemId(@Param("userId") final Long userId,
                                              @Param("itemId") final Long itemId);

    // Получение следующего бронирования для вещи
    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.item.id = :itemId
                AND b.start > :now
            ORDER BY b.start ASC
            """)
    Optional<Booking> findNextBooking(@Param("itemId") final Long itemId, @Param("now") final LocalDateTime now);

    // Получение последнего бронирования для вещи
    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.item.id = :itemId
                AND b.end < :now
            ORDER BY b.end DESC
            """)
    Optional<Booking> findLastBooking(@Param("itemId") final Long itemId, @Param("now") final LocalDateTime now);

}