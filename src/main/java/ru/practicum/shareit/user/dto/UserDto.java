package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.group.CreateGroup;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @Email(groups = {CreateGroup.class}, message = "Некорректно введен email пользователя.")
    @NotBlank(groups = {CreateGroup.class}, message = "Поле с электронной почтой пользователя не может быть пустым.")
    String email;
    int id;
    @NotBlank(groups = {CreateGroup.class}, message = "Имя пользователя не может быть пустым.")
    String name;

}