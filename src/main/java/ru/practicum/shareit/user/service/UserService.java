package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {

    // Добавление пользователя
    UserDto createUser(final UserDto userDto);

    // Удаление пользователя
    void deleteUser(final Integer userId);

    // Получение пользователе
    UserDto getUser(final Integer userId);

    // Обновление пользователя
    UserDto updateUser(final Integer userId, final UserDto userDto);

}