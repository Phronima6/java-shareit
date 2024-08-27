package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;
import java.util.Collection;

public interface UserRepository {

    // Добавление пользователя
    User createUser(final User user);

    // Удаление пользователя
    void deleteUser(final Integer userId);

    // Получение всех пользователей
    Collection<User> getAllUsers();

    // Получение пользователе
    User getUser(final Integer userId);

    // Обновление пользователя
    User updateUser(final Integer userId, final User user);

}