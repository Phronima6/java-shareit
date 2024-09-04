package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.group.CreateGroup;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {

    @NotNull(groups = {CreateGroup.class}, message = "Поле с пользователем, бронирующим вещь, не может быть пустым.")
    User booker;
    @Future(message = "Дата и время конца бронирования не может быть в прошлом.")
    LocalDateTime end;
    int id;
    @NotNull(groups = {CreateGroup.class}, message = "Поле с вещью не может быть пустым.")
    Item item;
    @PastOrPresent(message = "Дата и время начала бронирования не может быть в будущем.")
    LocalDateTime start;
    @NotNull(groups = {CreateGroup.class}, message = "Статус бронирования вещи не может быть пустым.")
    Status status;

}