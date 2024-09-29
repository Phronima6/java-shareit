package ru.practicum.shareit.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.request.model.ItemRequest;
import java.util.Collection;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    // Получение запросов пользователя, отсортированных по времени
    @Query("""
            SELECT ir
            FROM ItemRequest ir
            WHERE ir.requestor.id = :requestorId
            ORDER BY ir.created DESC
            """)
    Collection<ItemRequest> getAllByRequestor(@Param("requestorId") Long requestorId);

    // Получение запросов, не принадлежащих указанному пользователю и отсортированных по времени
    @Query("""
            SELECT ir
            FROM ItemRequest ir
            WHERE ir.requestor.id <> :requestorId
            ORDER BY ir.created DESC
            """)
    Collection<ItemRequest> findAllExcludingUserId(@Param("requestorId") Long requestorId);

}