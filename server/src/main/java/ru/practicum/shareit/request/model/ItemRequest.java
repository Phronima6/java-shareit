package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.model.User;
import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "item_requests")
public class ItemRequest {

    @Column(nullable = false)
    LocalDateTime created; // Дата и время создания запроса
    @Column(nullable = false)
    String description; // Описание запроса
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // Идентификатор запроса
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestor_id", nullable = false)
    User requestor; // Пользователь, создавший запрос

}