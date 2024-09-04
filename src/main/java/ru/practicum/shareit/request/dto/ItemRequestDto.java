package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.group.CreateGroup;
import ru.practicum.shareit.user.model.User;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestDto {

    @PastOrPresent(message = "Дата и время создания запроса не может быть в будущем.")
    LocalDateTime created;
    @NotBlank(groups = {CreateGroup.class}, message = "Описание требуемой вещи не может быть пустым.")
    String description;
    int id;
    @NotNull(groups = {CreateGroup.class}, message = "Поле с пользователем, создавшим запрос, не может быть пустым.")
    User requestor;

}