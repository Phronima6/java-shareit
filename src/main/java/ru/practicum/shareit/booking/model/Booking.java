package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "bookings")
public class Booking {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id", nullable = false)
    User booker; // Пользователь, который осуществляет бронирование
    @Column(name = "end_date", nullable = false)
    LocalDateTime end; // Дата и время конца бронирования
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id; // Идентификатор бронирования
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    Item item; // Вещь, которую пользователь бронирует
    @Transient
    Long itemId; // Идентификатор вещи
    @Column(name = "start_date", nullable = false)
    LocalDateTime start; // Дата и время начала бронирования
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Status status; // Статус бронирования вещи

}