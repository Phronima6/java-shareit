package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {

    // Добавление пользователя
    UserDto createUser(final UserDto userDto);

    // Удаление пользователя
    void deleteUser(final Long userId);

    // Получение пользователе
    UserDto getUser(final Long userId);

    // Обновление пользователя
    UserDto updateUser(final Long userId, final UserDto userDto);

}