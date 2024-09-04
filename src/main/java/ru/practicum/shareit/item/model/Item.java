package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {

    Boolean available; // Доступность вещи
    String description; // Описание вещи
    int id; // Идентификатор вещи
    String name; // Имя вещи
    User owner; // Владелец вещи
    ItemRequest request; // Если вещь создана по запросу;

}