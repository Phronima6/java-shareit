package ru.practicum.shareit.request.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.model.User;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {

    LocalDateTime created; // Дата и время создания запроса
    String description; // Описание запроса
    int id; // Идентификатор запроса
    User requestor; // Пользователь, создавший запрос

}