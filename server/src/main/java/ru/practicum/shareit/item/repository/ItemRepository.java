package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;
import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Получение всех вещей пользователя
    Collection<Item> findAllByOwnerId(final Long userId);

    // Получение всех вещей по идентификатору запроса
    @Query("""
            SELECT i
            FROM Item i
            WHERE i.request.id = :requestId
            """)
    Collection<Item> findAllByRequestId(@Param("requestId") Long requestId);

    // Поиск вещи по тексту
    @Query("""
            SELECT i
            FROM Item i
            WHERE i.available = true
                AND (LOWER(i.name) LIKE LOWER(CONCAT('%', :text, '%'))
                OR LOWER(i.description) LIKE LOWER(CONCAT('%', :text, '%')))
            """)
    Collection<Item> searchItem(@Param("text") final String text);

}