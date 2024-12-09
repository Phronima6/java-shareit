package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoInput {

    @Future(message = "Дата и время конца бронирования не может быть в прошлом.")
    LocalDateTime end;
    @NotNull(message = "Идентификатор вещи не может быть пустым.")
    Long itemId;
    @Future(message = "Дата и время начала бронирования не может быть в прошлом.")
    LocalDateTime start;

}