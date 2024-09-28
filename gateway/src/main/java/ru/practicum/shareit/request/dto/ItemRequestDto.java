package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.group.CreateGroup;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestDto {

    @NotBlank(groups = {CreateGroup.class}, message = "Описание запроса не может быть пустым.")
    String description;

}