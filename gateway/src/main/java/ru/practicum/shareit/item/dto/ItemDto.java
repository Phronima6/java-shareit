package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.group.CreateGroup;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {

    @NotNull(groups = {CreateGroup.class}, message = "Поле доступность вещи не может быть пустым.")
    Boolean available;
    @NotBlank(groups = {CreateGroup.class}, message = "Описание вещи не может быть пустым.")
    String description;
    @NotBlank(groups = {CreateGroup.class}, message = "Электронная почта пользователя не может быть пустой.")
    String name;
    Long requestId;

}