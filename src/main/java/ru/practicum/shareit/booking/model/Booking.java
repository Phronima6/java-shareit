package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {

    User booker; // Пользователь, который осуществляет бронирование
    LocalDateTime end; // Дата и время конца бронирования
    int id; // Идентификатор вещи
    Item item; // Вещь, которую пользователь бронирует
    LocalDateTime start; // Дата и время начала бронирования
    Status status; // Статус бронирования вещи

}