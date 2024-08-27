package ru.practicum.shareit.user.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    String email; // Электронная почта пользователя
    int id; // Идентификатор пользователя
    String name; // Имя пользователя

}